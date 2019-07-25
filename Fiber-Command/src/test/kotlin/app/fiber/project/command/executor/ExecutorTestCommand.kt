package app.fiber.project.command.executor

import app.fiber.project.command.SubTestCommand
import app.fiber.project.command.annotation.Model
import app.fiber.project.command.annotation.Parameter
import app.fiber.project.command.annotation.Parameters
import app.fiber.project.command.annotation.Route
import app.fiber.project.command.result.CommandResult

@Model("executor", "Executor test command for unit testing", ["alias"], [SubTestCommand::class])
class ExecutorTestCommand {

    @Route(description = "Default route description")
    fun default() = CommandResult.SUCCESS

    @Route("route", "Route description")
    fun route() = CommandResult.SUCCESS

    @Route("parameters", "Parameter route description")
    @Parameters(
        [
            Parameter("string", "String test parameter"),
            Parameter("number", "Int test parameter", Int::class)
        ]
    )
    fun routeWithParameters(string: String, number: Int) = CommandResult.SUCCESS

    @Route("optional", "Optional parameter route description")
    @Parameters(
        [
            Parameter("string", "String test parameter"),
            Parameter("optional", "Optional int test parameter", Int::class, true)
        ]
    )
    fun routeWithOptionalParameters(string: String, optional: Int?) = CommandResult.SUCCESS

}