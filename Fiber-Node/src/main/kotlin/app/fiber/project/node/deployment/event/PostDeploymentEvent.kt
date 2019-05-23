package app.fiber.project.node.deployment.event

import app.fiber.project.node.deployment.result.DeploymentResult
import app.fiber.project.node.event.Event

data class PostDeploymentEvent(val deploymentResult: DeploymentResult) : Event