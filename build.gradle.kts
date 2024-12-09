import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"

    id("org.jetbrains.kotlin.kapt") version "2.1.0"
}

group = "com.kafkawannafly"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val vertxVersion = "5.0.0.CR2"
val junitJupiterVersion = "5.9.1"

val mainVerticleName = "vertxtemplate.verticles.MainVerticle"
val launcherClassName = "vertxtemplate.AppLauncher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
    mainClass.set(launcherClassName)
}

dependencies {
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
    implementation("io.vertx:vertx-launcher-application")
    implementation("io.vertx:vertx-auth-jwt")
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-pg-client")
    implementation("io.vertx:vertx-rx-java3")
    implementation("com.ongres.scram:client:2.1")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("com.google.dagger:dagger:2.53")
    annotationProcessor("com.google.dagger:dagger-compiler:2.53")
    implementation("io.vertx:vertx-config:$vertxVersion")
    implementation("io.vertx:vertx-config-yaml:$vertxVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation("io.vertx:vertx-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
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