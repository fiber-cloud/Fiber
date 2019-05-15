import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin apply false
}

group = "app.fiber.project"
version = "1.0"

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    this.group = this.rootProject.group
    this.version = this.rootProject.version

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

