package app.fiber.project.command.result

/**
 * Enum of all possible execution results of an command.
 *
 * @author Tammo0987
 * @since 1.0
 */
enum class CommandResult {

    SUCCESS,
    FAILED,
    WRONG_PARAMETERS,
    COMMAND_NOT_FOUND,
    ROUTE_NOT_FOUND,
    CONVERTER_ERROR,
    UNUSABLE_INPUT

}