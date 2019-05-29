package app.fiber.project.node.template.resources

import app.fiber.project.node.io.createDirectories
import app.fiber.project.node.io.exists
import java.nio.file.Paths

class ResourcesTreeCreator {

    fun createResourcesTree() {
        listOf("resources/softwares", "resources/plugins", "resources/data")
            .map { Paths.get(it) }
            .filter { !it.exists() }
            .forEach { it.createDirectories() }
    }

}