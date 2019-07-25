package app.fiber.project.node.config

import app.fiber.project.ConfigFile
import app.fiber.project.node.software.ServerGroupRegistry
import app.fiber.project.yaml.MAPPER
import app.fiber.project.yaml.toYaml
import com.fasterxml.jackson.module.kotlin.readValue
import org.koin.core.KoinComponent
import org.koin.core.inject

class ServerGroupConfig : ConfigFile("server_groups"), KoinComponent {

    override fun load() {
        val serverGroupRegistry by inject<ServerGroupRegistry>()
        serverGroupRegistry.groups = MAPPER.readValue(this.path.toFile())
    }

    override fun save() {
        val serverGroupRegistry by inject<ServerGroupRegistry>()
        serverGroupRegistry.groups.toYaml(this.path)
    }

}