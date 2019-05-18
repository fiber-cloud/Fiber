package app.fiber.project.command

import app.fiber.project.command.result.CommandResult

/**
 * Stores information about a command and can execute it.
 *
 * @param [label] Label of the command.
 * @param [aliases] [Collection] of all possible aliases for [label].
 * @param [routes] [Collection] of all declared [CommandRoutes][CommandRoute].
 * @param [subModels] [Collection] of all declared sub [CommandModels][CommandModel].
 *
 * @author Tammo0987
 * @since 1.0
 */
class CommandModel(
    val label: String,
    val description: String,
    val aliases: Collection<String> = emptyList(),
    val routes: Collection<CommandRoute> = emptyList(),
    val subModels: Collection<CommandModel> = emptyList()
) {

    /**
     * Finds and executes a sub command or a [CommandRoute].
     *
     * @param [parameters] [List] of all parsed command arguments.
     *
     * @return [CommandResult] of the execution.
     */
    fun execute(parameters: List<String>): CommandResult {
        if (parameters.isEmpty()) return this.checkParameters()

        if (this.subModels.isNotEmpty()) {
            val label = parameters[0].toLowerCase()
            val subCommandModel = this.subModels.find { it.label.toLowerCase() == label }


            if (subCommandModel != null) {
                return subCommandModel.execute(parameters.drop(1))
            }
        }

        val route = this.route(parameters[0].toLowerCase()) ?: return CommandResult.ROUTE_NOT_FOUND
        return route.execute(parameters.drop(1))
    }

    /**
     * Finds a [CommandRoute] by name.
     *
     * @param [name] Name of the [CommandRoute] to find.
     *
     * @return If found, a [CommandRoute] instance, else null.
     */
    private fun route(name: String) = this.routes.find { it.name == name }

    private fun checkParameters(): CommandResult {
        val defaultRoute = this.route("") ?: return CommandResult.ROUTE_NOT_FOUND
        return defaultRoute.execute(emptyList())
    }

}