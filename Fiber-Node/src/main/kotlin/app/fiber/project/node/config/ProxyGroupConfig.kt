package app.fiber.project.node.config

import app.fiber.project.ConfigFile
import app.fiber.project.node.software.ProxyGroupRegistry
import app.fiber.project.yaml.fromYaml
import app.fiber.project.yaml.toYaml
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProxyGroupConfig : ConfigFile("proxy_groups"), KoinComponent {

    override fun load() {
        val proxyGroupRegistry by inject<ProxyGroupRegistry>()
        proxyGroupRegistry.groups = fromYaml(this.path)
    }

    override fun save() {
        val proxyGroupRegistry by inject<ProxyGroupRegistry>()
        proxyGroupRegistry.groups.toYaml(this.path)
    }
}