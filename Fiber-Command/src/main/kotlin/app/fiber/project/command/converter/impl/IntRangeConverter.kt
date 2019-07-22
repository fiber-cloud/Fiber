package app.fiber.project.command.converter.impl

import app.fiber.project.command.converter.ConvertException
import app.fiber.project.command.converter.InputConverter

/**
 * [InputConverter] implementation for type [IntRange].
 *
 * @author Tammo0987
 * @since 1.0
 */
object IntRangeConverter : InputConverter<IntRange> {

    /**
     * Convert the [input] to an object of type [IntRange].
     *
     * @param [input] Parsed input for a parameter that should be converted.
     *
     * @return Converted object of type [IntRange].
     */
    override fun convert(input: String): IntRange {
        try {
            val split = input.split("..")
            return IntRange(split[0].toInt(), split[1].toInt())
        } catch (e: Exception) {
            throw ConvertException(e.message!!)
        }
    }

    /**
     * Declared a [Collection] of compatible types, that could be converted with this implementation of an [InputConverter].
     *
     * @return [Collection] of compatible types.
     */
    override fun compatibleTypes() = listOf(IntRange::class)

}