package app.fiber.project.command.converter

import app.fiber.project.command.converter.impl.IntInputConverter
import app.fiber.project.command.converter.impl.IntRangeConverter
import app.fiber.project.command.converter.impl.StringInputConverter
import kotlin.reflect.KClass

/**
 * Registry for [InputConverters][InputConverter].
 *
 * @author Tammo0987
 * @since 1.0
 */
object InputConverterRegistry {

    /**
     * List of registered [InputConverter].
     */
    private val inputConverters =
        mutableListOf<InputConverter<*>>(StringInputConverter, IntInputConverter, IntRangeConverter)

    /**
     * Register an [InputConverter].
     *
     * @param [inputConverter] List of [InputConverter], that will be registered.
     */
    fun register(vararg inputConverter: InputConverter<*>) = this.inputConverters.addAll(inputConverter)

    /**
     * Get a specific [InputConverter] for a type [type].
     *
     * @param [type] Compatible type for the [InputConverter].
     *
     * @return Compatible [InputConverter] if found, else null.
     */
    fun inputConverter(type: KClass<*>) = this.inputConverters.find { it.compatibleTypes().contains(type) }

}