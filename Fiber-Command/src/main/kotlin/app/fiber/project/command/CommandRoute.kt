package app.fiber.project.command

import app.fiber.project.command.converter.InputConverterRegistry
import app.fiber.project.command.result.CommandResult
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaMethod

/**
 * Stores information about a command route and can execute it.
 *
 * @param [name] Name of the command route.
 * @param [description] Description of the command route.
 * @param [parameters] [Collection] of the [CommandParameter], that could be used for this [CommandRoute].
 * @param [function] [KFunction] where the route was declared.
 *
 * @author Tammo0987
 * @since 1.0
 */
data class CommandRoute(
    val name: String,
    val description: String,
    val parameters: Collection<CommandParameter>,
    private val function: KFunction<*>
) {

    /**
     * Executes this [CommandRoute].
     *
     * @param [parameters] [List] of parsed parameters for the execution.
     *
     * @return [CommandResult] of the execution.
     */
    fun execute(parameters: List<String>): CommandResult {
        val invokeInstance: Any = this.function.javaMethod!!.declaringClass.newInstance()

        if (this.parameters.isEmpty()) {
            if (parameters.isNotEmpty()) return CommandResult.WRONG_PARAMETERS

            return this.function.call(invokeInstance) as CommandResult
        } else {
            if(parameters.isEmpty()) return CommandResult.WRONG_PARAMETERS
        }

        if (this.hasOptionalParameters()) {
            when {
                parameters.size > this.parameters.size -> return CommandResult.WRONG_PARAMETERS

                parameters.size == this.parameters.size -> {
                    val invokeParameters = this.parameters.mapIndexed { index, commandParameter ->
                        try {
                            InputConverterRegistry.inputConverter(commandParameter.type)!!.convert(parameters[index])
                        } catch (e: Exception) {
                            e.printStackTrace()
                            return CommandResult.CONVERTER_ERROR
                        }
                    }

                    if (!this.function.isAccessible) {
                        this.function.isAccessible = true
                    }

                    return this.function.call(invokeInstance, *invokeParameters.toTypedArray()) as CommandResult
                }

                else -> {
                    val invokeParameters: MutableList<Any?> = parameters.mapIndexed { index, commandParameter ->
                        try {
                            InputConverterRegistry.inputConverter(this.parameters.elementAt(index).type)!!.convert(commandParameter)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            return CommandResult.CONVERTER_ERROR
                        }
                    } as MutableList<Any?>

                    repeat(this.parameters.size - parameters.size) {
                        invokeParameters.add(null)
                    }

                    if (!this.function.isAccessible) {
                        this.function.isAccessible = true
                    }

                    return this.function.call(invokeInstance, *invokeParameters.toTypedArray()) as CommandResult
                }
            }
        }

        if(parameters.size != this.parameters.size) return CommandResult.WRONG_PARAMETERS

        val invokeParameters = this.parameters.mapIndexed { index, commandParameter ->
            try {
                InputConverterRegistry.inputConverter(commandParameter.type)!!.convert(parameters[index])
            } catch (e: Exception) {
                e.printStackTrace()
                return CommandResult.CONVERTER_ERROR
            }
        }

        if (!this.function.isAccessible) {
            this.function.isAccessible = true
        }

        return this.function.call(invokeInstance, *invokeParameters.toTypedArray()) as CommandResult
    }

    /**
     * Check if any parameter is optional.
     *
     * @return [Boolean] if any parameter is optional.
     */
    private fun hasOptionalParameters(): Boolean {
        return this.parameters.any { it.optional }
    }

}