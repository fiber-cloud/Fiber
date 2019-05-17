import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin apply false
}

this.group = "app.fiber.project"
this.version = "1.0"

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    this.group = this.rootProject.group
    this.version = this.rootProject.version

    repositories {
        mavenCentral()
    }

    this.tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

defaultTasks("clean", "build")