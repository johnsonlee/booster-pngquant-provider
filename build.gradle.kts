import org.gradle.api.Project.DEFAULT_VERSION
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version embeddedKotlinVersion
    kotlin("kapt") version embeddedKotlinVersion
    id("io.johnsonlee.sonatype-publish-plugin") version "1.7.0"
}

group = "io.johnsonlee.booster"
version = project.findProperty("version")?.takeIf { it != DEFAULT_VERSION } ?: "1.0.0-SNAPSHOT"

dependencies {
    kapt("com.google.auto.service:auto-service:1.0.1")
    implementation(kotlin("stdlib"))
    implementation("com.google.auto.service:auto-service:1.0.1")
    implementation("com.didiglobal.booster:booster-command:2.3.0")
    implementation("com.didiglobal.booster:booster-kotlinx:2.3.0")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

allprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        google()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xskip-metadata-version-check")
            jvmTarget = "1.8"
        }
    }
}