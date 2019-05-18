package app.fiber.project.command.composer

import app.fiber.project.command.CommandParameter
import app.fiber.project.command.annotation.Parameter
import app.fiber.project.command.annotation.Parameters
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.valueParameters

/**
 * Composing a [Collection] of [CommandParameters][CommandParameter] from a [KFunction], which has [Parameter] or a [Parameters] annotation.
 *
 * @author Tammo0987
 * @since 1.0
 */
object CommandParameterComposer {

    /**
     * Composing and scanning a [KFunction] to create a [Collection] of [CommandParameter] instances.
     *
     * @param [function] [KFunction] to compose.
     *
     * @return [Collection] of [CommandParameter].
     */
    fun compose(function: KFunction<*>): Collection<CommandParameter> = when {
        function.findAnnotation<Parameters>() != null -> {
            function.findAnnotation<Parameters>()!!.value.mapIndexed { index, parameter ->
                composeParameter(index, parameter, function)
            }
        }

        function.findAnnotation<Parameter>() != null -> {
            listOf(composeParameter(0, function.findAnnotation()!!, function))
        }

        else -> emptyList()
    }

    /**
     * Compose a specific [Parameter].
     *
     * @param [index] Index of the parameter on the [function].
     * @param [parameter] [Parameter] annotation to get information about the parameter.
     * @param [function] [KFunction] to check if a optional parameter is nullable.
     *
     * @return [CommandParameter].
     */
    private fun composeParameter(index: Int, parameter: Parameter, function: KFunction<*>): CommandParameter {
        if (parameter.optional && !function.valueParameters[index].type.isMarkedNullable) {
            throw IllegalStateException("Function parameter ${parameter.name} should be nullable!")
        }

        return CommandParameter(index, parameter.name, parameter.description, parameter.type, parameter.optional)
    }

}