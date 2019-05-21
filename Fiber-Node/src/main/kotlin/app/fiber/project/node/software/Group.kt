package app.fiber.project.node.software

import java.util.*

abstract class Group(
    open val name: String,
    open val uuid: UUID,
    open var instances: IntRange
)