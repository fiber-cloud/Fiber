package app.fiber.project.node.pipeline.impl

import app.fiber.project.ConfigRegistry
import app.fiber.project.command.converter.InputConverterRegistry
import app.fiber.project.command.executor.CommandExecutor
import app.fiber.project.node.command.GroupCommand
import app.fiber.project.node.command.StopCommand
import app.fiber.project.node.command.converter.ServerTypeConverter
import app.fiber.project.node.config.DeploymentProfileConfig
import app.fiber.project.node.config.ProxyGroupConfig
import app.fiber.project.node.config.ServerGroupConfig
import app.fiber.project.node.deployment.schedule.DeploymentScheduler
import app.fiber.project.node.pipeline.Pipeline
import app.fiber.project.node.pipeline.Stage
import app.fiber.project.node.template.TemplateRegistry
import app.fiber.project.node.template.resources.ResourcesTreeCreator
import org.koin.core.KoinComponent
import org.koin.core.inject

class StartPipeline : Pipeline, KoinComponent {

    @Stage(0)
    fun registerConverter() = InputConverterRegistry.register(ServerTypeConverter())

    @Stage(1)
    fun registerCommands() = CommandExecutor.register(
        StopCommand::class,
        GroupCommand::class
    )

    @Stage(2)
    fun loadConfigs() {
        ConfigRegistry.register(DeploymentProfileConfig(), ServerGroupConfig(), ProxyGroupConfig())
        ConfigRegistry.init()
    }

    @Stage(3)
    fun createResourcesTree() = ResourcesTreeCreator().createResourcesTree()

    @Stage(4)
    fun loadTemplates() = inject<TemplateRegistry>().value.loadTemplates()

    @Stage(5)
    fun startDeploymentScheduler() = inject<DeploymentScheduler>().value.start()

}