package app.fiber.project.node.software.server

import app.fiber.project.node.software.Group
import java.util.*

data class ServerGroup(
    override val name: String,
    override val uuid: UUID,
    override var instances: IntRange,
    var priority: Int,
    val type: ServerType
) : Group(name, uuid, instances)