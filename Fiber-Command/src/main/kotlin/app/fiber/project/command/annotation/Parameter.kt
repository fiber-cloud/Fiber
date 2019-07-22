package app.fiber.project.command.annotation

import kotlin.reflect.KClass

/**
 * Declares a [app.fiber.project.command.CommandParameter].
 *
 * @property [name] Name of the parameter.
 * @property [type] Typ of the parameter, to convert the input string into the type of the parameter for a better usage.
 * @property [description] Description of the parameter.
 * @property [optional] Declares, if the parameter is optional to provide to execute the command.
 *
 * @author Tammo0987
 * @since 1.0
 */
@Retention
@Target(AnnotationTarget.FUNCTION)
annotation class Parameter(
    val name: String,
    val description: String,
    val type: KClass<*> = String::class,
    val optional: Boolean = false
)