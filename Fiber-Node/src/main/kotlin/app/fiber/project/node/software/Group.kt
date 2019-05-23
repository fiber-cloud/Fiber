package app.fiber.project.node.software

import app.fiber.project.node.template.Template
import java.util.*

abstract class Group(
    open val name: String,
    open val uuid: UUID,
    open var instances: IntRange,
    open var template: Template
)