package app.fiber.project.command.converter

/**
 * The [ConvertException] should be thrown, if an [InputConverter] fails to convert the input.
 *
 * @author Tammo0987
 * @since 1.0
 */
class ConvertException(message: String) : Exception(message)