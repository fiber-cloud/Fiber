package app.fiber.project.command.annotation

import kotlin.reflect.KClass

/**
 * Declares a [app.fiber.project.command.CommandModel].
 *
 * @property [label] Label to identify the command.
 * @property [aliases] Optional array of aliases, where the command could also be identified with.
 * @property [subModels] Optional array of sub commands, which are also declared by [Model].
 *
 * @author Tammo0987
 * @since 1.0
 */
@Retention
@Target(AnnotationTarget.CLASS)
annotation class Model(
    val label: String,
    val description: String,
    val aliases: Array<String> = [],
    val subModels: Array<KClass<*>> = []
)