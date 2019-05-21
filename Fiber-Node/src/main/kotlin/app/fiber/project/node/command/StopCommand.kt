package app.fiber.project.node.command

import app.fiber.project.command.annotation.Model
import app.fiber.project.command.annotation.Route
import app.fiber.project.command.result.CommandResult

@Model("stop", "Stops the node", ["shutdown", "exit"])
class StopCommand {

    @Route(description = "Stops the node")
    fun default(): CommandResult {
        System.exit(0)
        return CommandResult.SUCCESS
    }

}