package app.fiber.project.node.software.proxy

import app.fiber.project.node.software.Group
import java.util.*

data class ProxyGroup(
    override val name: String,
    override val uuid: UUID,
    override var instances: IntRange,
    override var template: String,
    var fallback: String?
) : Group(name, uuid, instances, template)