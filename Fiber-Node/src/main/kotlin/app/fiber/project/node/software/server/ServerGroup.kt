package app.fiber.project.node.software.server

import app.fiber.project.node.software.Group
import app.fiber.project.node.template.Template
import java.util.*

data class ServerGroup(
    override val name: String,
    override val uuid: UUID,
    override val instances: IntRange,
    override val template: String,
    override val priority: Int,
    val type: ServerType
) : Group(name, uuid, instances, template, priority)