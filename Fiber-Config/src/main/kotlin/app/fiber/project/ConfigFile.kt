package app.fiber.project

import java.nio.file.Files
import java.nio.file.Paths

abstract class ConfigFile(name: String) {

    protected val path = Paths.get("config/$name.yaml")

    init {
        if (Files.notExists(this.path.parent)) Files.createDirectories(this.path.parent)

        if (Files.notExists(this.path)) this.save()
    }

    abstract fun load()

    abstract fun save()

}