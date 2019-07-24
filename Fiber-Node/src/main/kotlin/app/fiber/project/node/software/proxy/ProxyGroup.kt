package app.fiber.project.node.software.proxy

import app.fiber.project.node.software.Group
import java.util.*

data class ProxyGroup(
    override val name: String,
    override val uuid: UUID,
    override val instances: IntRange,
    override val template: String,
    override val priority: Int,
    var fallback: String?
) : Group(name, uuid, instances, template, priority)