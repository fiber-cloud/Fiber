package app.fiber.project.node.deployment.impl.process.result

import app.fiber.project.node.deployment.result.DeployResult

//TODO streams
data class ProcessDeployResult(val process: Process, override val timeToStart: Long) : DeployResult(timeToStart)