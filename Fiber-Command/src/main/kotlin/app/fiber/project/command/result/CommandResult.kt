package app.fiber.project.command.result

/**
 * Enum of all possible execution results of an command.
 *
 * @author Tammo0987
 * @since 1.0
 */
enum class CommandResult {

    /**
     * Command executed successfully.
     */
    SUCCESS,

    /**
     * Command execution failed.
     */
    FAILED,

    /**
     * Size of parameters are not right.
     */
    WRONG_PARAMETERS,

    /**
     * Command not exists.
     */
    COMMAND_NOT_FOUND,

    /**
     * Command route not found.
     */
    ROUTE_NOT_FOUND,

    /**
     * Parameters could not be converted.
     */
    CONVERTER_ERROR,

    /**
     * The input is not a valid command.
     */
    UNUSABLE_INPUT

}