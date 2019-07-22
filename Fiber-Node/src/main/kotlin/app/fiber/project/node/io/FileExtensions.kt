package app.fiber.project.node.io

import java.nio.file.Files
import java.nio.file.Path

fun Path.exists(): Boolean = Files.exists(this)

fun Path.createDirectories(): Path = Files.createDirectories(this)

fun Path.createFile(): Path = Files.createFile(this)

fun Path.listFiles(): List<Path> = (this.toFile().listFiles()?: emptyList()).map { it.toPath() }

fun Path.isDirectory(): Boolean = this.toFile().isDirectory

fun Path.copy(destination: Path) = this.toFile().copyRecursively(destination.toFile(), true)

fun Path.delete() = this.toFile().deleteRecursively()