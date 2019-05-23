package app.fiber.project.node

import app.fiber.project.command.executor.CommandExecutor
import app.fiber.project.command.result.CommandResult
import app.fiber.project.node.logging.Logger
import app.fiber.project.node.logging.asciiArt
import app.fiber.project.node.pipeline.impl.StartPipeline
import app.fiber.project.node.pipeline.impl.StopPipeline
import org.koin.core.KoinComponent
import org.koin.core.inject

object Node : KoinComponent {

    private val logger by inject<Logger>()

    fun start() {
        this.logger.info("Starting node...", false)

        asciiArt.split("\n").forEach { this.logger.info(it) }

        StartPipeline().execute()

        while (true) {
            val input = readLine() ?: break

            val warning: String = when (CommandExecutor.execute(input)) {
                CommandResult.WRONG_PARAMETERS -> "Parameters were not used right!"
                CommandResult.COMMAND_NOT_FOUND -> "Command not found!"
                CommandResult.ROUTE_NOT_FOUND -> "Route not found!"
                CommandResult.UNUSABLE_INPUT -> "The input could not be processed!"
                else -> ""
            }

            if (warning.isNotEmpty()) this@Node.logger.warn(warning)
        }
    }

    fun stop() {
        this.logger.info("Stopping node")

        StopPipeline().execute()

        this.logger.info("Node stopped")
    }

    fun version() = this.javaClass.`package`.implementationVersion ?: "dev version"

}