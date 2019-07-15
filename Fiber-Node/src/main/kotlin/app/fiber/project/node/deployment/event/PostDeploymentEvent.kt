package app.fiber.project.node.deployment.event

import app.fiber.project.node.addon.Event
import app.fiber.project.node.deployment.result.DeploymentResult

data class PostDeploymentEvent(val deploymentResult: DeploymentResult) : Event