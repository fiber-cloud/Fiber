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

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

this.tasks.register<DependencyTask>("collectDependencies") {
    this.group = "fiber"
    this.description = "Collect all dependencies for the runtime in the dependencies.json file"
}
