package app.fiber.project.command

import kotlin.reflect.KClass

/**
 * Stores information about a command parameter.
 *
 * @param [index] Index of the parameter.
 * @param [name] Name of the parameter.
 * @param [description] Description of the parameter.
 * @param [type] Type of the parameter.
 * @param [optional] Declares, if the parameter is optional to use.
 *
 * @author Tammo0987
 * @since 1.0
 */
data class CommandParameter(
        val index: Int,
        val name: String,
        val description: String,
        val type: KClass<*>,
        val optional: Boolean
)