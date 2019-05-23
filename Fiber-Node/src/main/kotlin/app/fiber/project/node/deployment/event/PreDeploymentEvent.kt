package app.fiber.project.node.deployment.event

import app.fiber.project.node.deployment.JarDeployUnit
import app.fiber.project.node.event.Event

data class PreDeploymentEvent(val jarDeployUnit: JarDeployUnit) : Event