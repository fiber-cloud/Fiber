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

    override fun convert(input: String): Int {
        try {
            return input.toInt()
        } catch (e: Exception) {
            throw ConvertException(e.message!!)
        }
    }

    override fun compatibleTypes() = listOf(Int::class)

}