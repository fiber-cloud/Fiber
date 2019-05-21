package app.fiber.project.node.software.proxy

import app.fiber.project.node.software.Group
import app.fiber.project.node.software.server.ServerGroup
import java.util.*

data class ProxyGroup(
    override val name: String,
    override val uuid: UUID,
    override var minInstances: Int,
    override var maxInstances: Int,
    var fallback: ServerGroup?
) : Group(name, uuid, minInstances, maxInstances)