package app.fiber.project.node.pipeline.impl

import app.fiber.project.node.deployment.schedule.DeploymentScheduler
import app.fiber.project.node.pipeline.Pipeline
import app.fiber.project.node.pipeline.Stage
import org.koin.core.KoinComponent
import org.koin.core.inject

class StopPipeline : Pipeline, KoinComponent {

    @Stage(0)
    fun stopDeploymentScheduler() = inject<DeploymentScheduler>().value.stop()
    
}