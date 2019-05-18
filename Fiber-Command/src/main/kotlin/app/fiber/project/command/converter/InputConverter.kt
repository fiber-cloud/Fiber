package app.fiber.project.command.converter

import kotlin.reflect.KClass

/**
 * Interface to implement multiple converters, to convert an input of type [String], to a specific type automatically.
 *
 * @param [T] Type of the object, where the input should converted to.
 *
 * @author Tammo0987
 * @since 1.0
 */
interface InputConverter<T> {

    /**
     * Convert the [input] to an object of type [T].
     *
     * @param [input] Parsed input for a parameter that should be converted.
     *
     * @return Converted object of type [T].
     */
    fun convert(input: String): T

    /**
     * Declared a [Collection] of compatible types, that could be converted with this implementation of an [InputConverter].
     *
     * @return [Collection] of compatible types.
     */
    fun compatibleTypes(): Collection<KClass<*>>

}