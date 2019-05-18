import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id(Plugins.shadowJar) version Versions.shadow
}

repositories {
    jcenter()
}

dependencies {
    implementation(Dependencies.kotlinStandard)
    implementation(Dependencies.kotlinReflect)

    implementation(Dependencies.joptSimple)
    implementation(Dependencies.koin)
    implementation(Dependencies.zip4j)
}

this.tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "app.fiber.project.node.NodeBootstrapKt"
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Built-By"] = System.getProperty("user.name")
        attributes["Build-Timestamp"] = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
        attributes["Build-Jdk"] = "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${System.getProperty("java.vm.version")})"
        attributes["Build-OS"] = "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${System.getProperty("os.version")}"
    }

    this.archiveClassifier.set("")
}

tasks {

    val shadowJar by existing

    build {
        dependsOn(shadowJar)
    }
}