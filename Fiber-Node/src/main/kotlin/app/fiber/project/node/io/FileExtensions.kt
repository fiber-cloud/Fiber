package app.fiber.project.node.io

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun Path.exists(): Boolean = Files.exists(this)

fun Path.createDirectories(): Path = Files.createDirectories(this)

fun Path.createFile(): Path = Files.createFile(this)

fun Path.listFiles(): List<Path> = this.toFile().listFiles().map { it.toPath() }

fun Path.isDirectory(): Boolean = this.toFile().isDirectory

fun Path.copy(destination: Path) {
    if (!destination.exists()) destination.createDirectories()

    //TODO streams?
    this.listFiles().forEach {
        if (it.isDirectory()) {
            it.copy(Paths.get(it.toAbsolutePath().toString(), it.toString()))
        } else {
            Files.copy(it, Paths.get(destination.toAbsolutePath().toString(), it.toString()), StandardCopyOption.REPLACE_EXISTING)
        }
    }

}

fun Path.delete() {
    Files.walk(this)
        .sorted(Comparator.reverseOrder())
        .forEach { Files.delete(it) }
}