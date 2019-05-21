package app.fiber.project.node.configuration

import app.fiber.project.node.software.proxy.ProxyGroupRegistry
import app.fiber.project.node.software.server.ServerGroupRegistry

data class RunConfiguration(
    val name: String,
    val proxyGroupRegistry: ProxyGroupRegistry,
    val serverGroupRegistry: ServerGroupRegistry
)