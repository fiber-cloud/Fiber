package app.fiber.project.node.pipeline.impl

import app.fiber.project.command.converter.InputConverterRegistry
import app.fiber.project.command.executor.CommandExecutor
import app.fiber.project.node.addon.AddonManager
import app.fiber.project.node.command.GroupCommand
import app.fiber.project.node.command.StopCommand
import app.fiber.project.node.command.converter.ServerTypeConverter
import app.fiber.project.node.pipeline.Pipeline
import app.fiber.project.node.pipeline.Stage
import org.koin.core.KoinComponent
import org.koin.core.inject

class StartPipeline : Pipeline, KoinComponent {

    private val addonManager by inject<AddonManager>()

    @Stage(0)
    fun initAddonManager() = this.addonManager.init()

    @Stage(1)
    fun registerConverter() = InputConverterRegistry.register(ServerTypeConverter())

    @Stage(2)
    fun registerCommands() = CommandExecutor.register(
        StopCommand::class,
        GroupCommand::class
    )

}
