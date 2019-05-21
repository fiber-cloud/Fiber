package app.fiber.project.node.software.server

import app.fiber.project.node.software.Group
import java.util.*

data class ServerGroup(
    override val name: String,
    override val uuid: UUID,
    override var minInstances: Int,
    override var maxInstances: Int,
    var priority: Int,
    val type: ServerType
) : Group(name, uuid, minInstances, maxInstances)