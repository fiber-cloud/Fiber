package app.fiber.project.node.configuration

import app.fiber.project.node.software.ProxyGroupRegistry
import app.fiber.project.node.software.ServerGroupRegistry

data class RunConfiguration(
    val name: String,
    val proxyGroupRegistry: ProxyGroupRegistry,
    val serverGroupRegistry: ServerGroupRegistry
)