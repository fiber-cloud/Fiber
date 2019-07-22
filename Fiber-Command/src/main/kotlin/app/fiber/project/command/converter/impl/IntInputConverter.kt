package app.fiber.project.command.converter.impl

import app.fiber.project.command.converter.ConvertException
import app.fiber.project.command.converter.InputConverter

/**
 * [InputConverter] implementation for type [Int].
 *
 * @author Tammo0987
 * @since 1.0
 */
object IntInputConverter : InputConverter<Int> {

    /**
     * Convert the [input] to an object of type [Int].
     *
     * @param [input] Parsed input for a parameter that should be converted.
     *
     * @return Converted object of type [Int].
     */
    override fun convert(input: String): Int {
        try {
            return input.toInt()
        } catch (e: Exception) {
            throw ConvertException(e.message!!)
        }
    }

    /**
     * Declared a [Collection] of compatible types, that could be converted with this implementation of an [InputConverter].
     *
     * @return [Collection] of compatible types.
     */
    override fun compatibleTypes() = listOf(Int::class)

}