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
            return this.executeWithNoParameters(invokeInstance)
        } else {
            if (parameters.isEmpty()) return CommandResult.WRONG_PARAMETERS
        }

        val convertedParameters = try {
            this.convertParameters(parameters)
        } catch (e: Exception) {
            e.printStackTrace()
            return CommandResult.CONVERTER_ERROR
        }

        if (this.hasOptionalParameters()) this.executeWithOptionalParameters(parameters, invokeInstance, convertedParameters)

        return this.executeWithParameters(invokeInstance, convertedParameters)
    }

    /**
     * Executes this [CommandRoute] if it has no [CommandParameter]s.
     *
     * @param [invokeInstance] Instance to call the function.
     *
     * @return [CommandResult] of the execution.
     */
    private fun executeWithNoParameters(invokeInstance: Any): CommandResult {
        if (parameters.isNotEmpty()) return CommandResult.WRONG_PARAMETERS

        return this.function.call(invokeInstance) as CommandResult
    }

    /**
     * Executes this [CommandRoute] if it has optional [CommandParameter]s.
     *
     * @param [parameters] Parameters to execute the function.
     * @param [invokeInstance] Instance to call the function.
     * @param [convertedParameters] Converted parameters to execute the function.
     *
     * @return [CommandResult] of the execution.
     */
    private fun executeWithOptionalParameters(
        parameters: List<String>,
        invokeInstance: Any,
        convertedParameters: List<Any?>
    ): CommandResult {
        when {
            parameters.size > this.parameters.size -> return CommandResult.WRONG_PARAMETERS

            parameters.size == this.parameters.size -> {
                this.checkAccess()

                return this.function.call(invokeInstance, *convertedParameters.toTypedArray()) as CommandResult
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

                this.checkAccess()

                return this.function.call(invokeInstance, *invokeParameters.toTypedArray()) as CommandResult
            }
        }
    }

    /**
     * Executes this [CommandRoute] if it has [CommandParameter]s.
     *
     * @param [invokeInstance] Instance to call the function.
     * @param [convertedParameters] Converted parameters to execute the function.
     *
     * @return [CommandResult] of the execution.
     */
    private fun executeWithParameters(invokeInstance: Any, convertedParameters: List<Any?>): CommandResult {
        if (parameters.size != this.parameters.size) return CommandResult.WRONG_PARAMETERS

        this.checkAccess()

        return this.function.call(invokeInstance, *convertedParameters.toTypedArray()) as CommandResult
    }

    /**
     * Converts [parameters] to their type.
     *
     * @param [parameters] Parameters to convert.
     *
     * @return Converted parameters.
     */
    private fun convertParameters(parameters: List<String>): List<Any?> {
        return this.parameters.mapIndexed { index, commandParameter ->
            InputConverterRegistry.inputConverter(commandParameter.type)!!.convert(parameters[index])
        }
    }

    /**
     * Check if any parameter is optional.
     *
     * @return [Boolean] if any parameter is optional.
     */
    private fun hasOptionalParameters(): Boolean {
        return this.parameters.any { it.optional }
    }

    /**
     * Check access of the function.
     */
    private fun checkAccess() {
        if (!this.function.isAccessible) this.function.isAccessible = true
    }

}