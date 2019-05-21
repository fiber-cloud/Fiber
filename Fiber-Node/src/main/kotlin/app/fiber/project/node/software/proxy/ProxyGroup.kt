package app.fiber.project.node.software.proxy

import app.fiber.project.node.software.Group
import app.fiber.project.node.software.server.ServerGroup
import java.util.*

data class ProxyGroup(
    override val name: String,
    override val uuid: UUID,
    override var instances: IntRange,
    var fallback: ServerGroup?
) : Group(name, uuid, instances)