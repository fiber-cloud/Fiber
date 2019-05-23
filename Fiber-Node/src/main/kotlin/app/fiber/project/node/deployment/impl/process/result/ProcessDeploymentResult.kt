package app.fiber.project.node.deployment.impl.process.result

import app.fiber.project.node.deployment.result.DeploymentResult

data class ProcessDeploymentResult(val process: Process, override val timeToStart: Long) : DeploymentResult(timeToStart)