import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.yaml.snakeyaml.Yaml

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jetbrains.kotlin.kapt") version "2.1.0"
    id("com.diffplug.spotless") version "7.0.0.BETA4"
    id("checkstyle")
    id("org.liquibase.gradle") version "2.2.0"
}

buildscript {
    dependencies {
        classpath("org.yaml:snakeyaml:2.3")
    }
}

group = "com.kafkawannafly"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}


val mainVerticleName = "vertxtemplate.verticles.MainVerticle"
val launcherClassName = "vertxtemplate.AppLauncher"

application {
    mainClass.set(launcherClassName)
}

dependencies {
    val lombokVersion = "1.18.36"
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    val vertxVersion = "5.0.2"
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
    implementation("io.vertx:vertx-auth-jwt:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-pg-client:$vertxVersion")
    implementation("com.ongres.scram:scram-client:3.1")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("org.hibernate:hibernate-validator:8.0.2.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("org.glassfish:jakarta.el:5.0.0-M1")

    implementation("io.vertx:vertx-codegen:$vertxVersion")
    implementation("io.vertx:vertx-sql-client-templates:$vertxVersion")
    annotationProcessor("io.vertx:vertx-sql-client-templates:$vertxVersion")
    annotationProcessor("io.vertx:vertx-codegen:$vertxVersion:processor")

    val daggerVersion = "2.53"
    implementation("com.google.dagger:dagger:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation("org.apache.commons:commons-lang3:3.18.0")

    val log4jVersion = "2.24.3"
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")

    val smallRyeVersion = "3.10.2"
    implementation("io.smallrye.config:smallrye-config:$smallRyeVersion")
    implementation("io.smallrye.config:smallrye-config-source-yaml:$smallRyeVersion")

    val junitJupiterVersion = "5.9.1"
    testImplementation("io.vertx:vertx-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

    val jacksonVersion = "2.18.3"
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    // Set up for 2 scopes: Gradlew commandline and programmatically migrate
    val liquibase = "org.liquibase:liquibase-core:4.30.0"
    liquibaseRuntime(liquibase)
    implementation(liquibase)
    val postgresql = "org.postgresql:postgresql:42.7.2"
    liquibaseRuntime(postgresql)
    implementation(postgresql)

    liquibaseRuntime("org.yaml:snakeyaml:2.3")
    liquibaseRuntime("info.picocli:picocli:4.7.5")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("fat")
    manifest {
        attributes(mapOf("Main-Verticle" to mainVerticleName))
    }
    mergeServiceFiles()
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(PASSED, SKIPPED, FAILED)
    }
}

tasks.withType<JavaExec> {
    args = listOf(mainVerticleName)
}

spotless {
    isEnforceCheck = true
    java {
        removeUnusedImports()
        palantirJavaFormat().formatJavadoc(true)
        formatAnnotations()
        importOrder("", "java|javax", "\\#")
        trimTrailingWhitespace()
        targetExclude("**/generated/**", "**/build/**")
    }
}

checkstyle {
    toolVersion = "10.20.2"
    configFile = file("$rootDir/code-style-google.xml")
}

tasks.check {
    dependsOn("spotlessCheck", "checkstyleMain")
}

liquibase {
    activities {
        create("main") {
            val yaml = Yaml()
            val inputStream = File("src/main/resources/application.yaml").inputStream()
            val config = yaml.load<Map<String, Any>>(inputStream)

            val app = config["app"] as Map<*, *>
            val dbConfig = app["db"] as Map<*, *>

            val host = dbConfig["host"]
            val port = dbConfig["port"]
            val name = dbConfig["name"]
            val user = dbConfig["user"]
            val password = dbConfig["password"]

            arguments = mapOf(
                "changelogFile" to "src/main/resources/db/changelog/master.yaml",
                "url" to "jdbc:postgresql://$host:$port/$name",
                "username" to user,
                "password" to password
            )
        }
    }
    runList = "main"
}

val changelogPath = "src/main/resources/db/changelog/master.yaml"
val changesetDir = "src/main/resources/db/changelog/changesets"

tasks.register("addChangeSet") {
    doLast {
        val inputName = project.findProperty("n") as String?
        if (inputName.isNullOrBlank()) {
            throw GradleException("Usage: ./gradlew addChangeSet -Pn=your_change_name")
        }

        val changelog = File(changelogPath)
        val idRegex = Regex("""id:\s*(\d+)""")
        val lastId = idRegex.findAll(changelog.readText())
            .map { it.groupValues[1].toInt() }
            .maxOrNull() ?: 0
        val newId = lastId + 1

        val upFile = "${newId}_${inputName}.up.sql"
        val downFile = "${newId}_${inputName}.down.sql"
        val upPath = "$changesetDir/$upFile"
        val downPath = "$changesetDir/$downFile"

        File(upPath).writeText("-- Up migration for $inputName\n")
        File(downPath).writeText("-- Down migration for $inputName\n")

        val gitUser = ProcessBuilder("git", "config", "user.name")
            .redirectErrorStream(true)
            .start()
            .inputStream
            .bufferedReader()
            .readText()
            .trim()
            .take(255)

        val changeSetYaml = """
  - changeSet:
      id: $newId
      author: $gitUser
      changes:
        - sqlFile:
            path: ./changesets/$upFile
            relativeToChangelogFile: true
      rollback:
        - sqlFile:
            path: ./changesets/$downFile
            relativeToChangelogFile: true
""".trimEnd()

        changelog.appendText("\n$changeSetYaml")
        println("Created $upPath, $downPath and updated $changelogPath")
    }
}