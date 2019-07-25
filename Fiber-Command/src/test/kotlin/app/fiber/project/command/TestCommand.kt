package app.fiber.project.command

import app.fiber.project.command.annotation.Model
import app.fiber.project.command.annotation.Parameter
import app.fiber.project.command.annotation.Parameters
import app.fiber.project.command.annotation.Route
import app.fiber.project.command.result.CommandResult

@Model("test", "Test command for unit testing", ["alias"], [SubTestCommand::class])
class TestCommand {

    @Route(description = "Route with no parameters")
    fun default() = CommandResult.SUCCESS

    @Route("parameter", "Route with parameters")
    @Parameters([
        Parameter("test", "Test parameter"),
        Parameter("optional", "Optional parameter", optional = true)
    ])
    fun parameter(test: String, optional: String?) = CommandResult.SUCCESS

}

@Model("sub", "Sub command for unit testing")
class SubTestCommand {

    @Route(description = "Route with no parameters")
    fun default() = CommandResult.SUCCESS

    @Route("parameter", "Route with parameters")
    @Parameters([
        Parameter("test", "Test parameter"),
        Parameter("optional", "Optional parameter", optional = true)
    ])
    fun parameter(test: String, optional: String?) = CommandResult.SUCCESS

}