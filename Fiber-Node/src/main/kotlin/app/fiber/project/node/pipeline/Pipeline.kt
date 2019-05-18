package app.fiber.project.node.pipeline

import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation

interface Pipeline {

    fun execute() = this::class.declaredFunctions
            .filter { it.findAnnotation<Stage>() != null }
            .sortedBy { it.findAnnotation<Stage>()!!.step }
            .forEach { it.call(this) }
}