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

val vertxVersion = "5.0.0.CR2"
val junitJupiterVersion = "5.9.1"
val lombokVersion = "1.18.36"

val mainVerticleName = "vertxtemplate.verticles.MainVerticle"
val launcherClassName = "vertxtemplate.AppLauncher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
    mainClass.set(launcherClassName)
}

dependencies {
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
    implementation("io.vertx:vertx-auth-jwt")
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-pg-client")
    implementation("com.ongres.scram:client:2.1")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("com.google.dagger:dagger:2.53")
    annotationProcessor("com.google.dagger:dagger-compiler:2.53")
    implementation("io.vertx:vertx-config:$vertxVersion")
    implementation("io.vertx:vertx-config-yaml:$vertxVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.apache.commons:commons-lang3:3.17.0")

    testImplementation("io.vertx:vertx-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    liquibaseRuntime("org.liquibase:liquibase-core:4.30.0")
    liquibaseRuntime("org.yaml:snakeyaml:2.3")
    liquibaseRuntime("org.postgresql:postgresql:42.7.2")
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
        importOrder("java|javax", "vertxtemplate", "com", "\\#vertxtemplate", "\\#", "")
        removeUnusedImports()
        palantirJavaFormat().formatJavadoc(true)
        formatAnnotations()
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
            val dbConfig = config["db"] as Map<*, *>

            val host = dbConfig["host"]
            val port = dbConfig["port"]
            val name = dbConfig["name"]
            val user = dbConfig["user"]
            val password = dbConfig["password"]

            arguments = mapOf(
                "changeLogFile" to "src/main/resources/db/changelog/master.yaml",
                "url" to "jdbc:postgresql://$host:$port/$name",
                "username" to user,
                "password" to password
            )
        }
    }
    runList = "main"
}