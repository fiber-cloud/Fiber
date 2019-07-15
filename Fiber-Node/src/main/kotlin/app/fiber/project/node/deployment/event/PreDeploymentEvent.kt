package app.fiber.project.node.deployment.event

import app.fiber.project.node.addon.Event
import app.fiber.project.node.deployment.JarDeployUnit

data class PreDeploymentEvent(val jarDeployUnit: JarDeployUnit) : Event