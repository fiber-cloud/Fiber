package app.fiber.project.node.config

import app.fiber.project.ConfigFile
import app.fiber.project.node.software.ProxyGroupRegistry
import app.fiber.project.yaml.MAPPER
import app.fiber.project.yaml.toYaml
import com.fasterxml.jackson.module.kotlin.readValue
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProxyGroupConfig : ConfigFile("proxy_groups"), KoinComponent {

    override fun load() {
        val proxyGroupRegistry by inject<ProxyGroupRegistry>()
        proxyGroupRegistry.groups = MAPPER.readValue(this.path.toFile())
    }

    override fun save() {
        val proxyGroupRegistry by inject<ProxyGroupRegistry>()
        proxyGroupRegistry.groups.toYaml(this.path)
    }
}