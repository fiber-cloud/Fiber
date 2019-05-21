package app.fiber.project.node.deployment.event

import app.fiber.project.node.deployment.JarDeployable
import app.fiber.project.node.event.Event

data class PreDeployEvent(val jarDeployable: JarDeployable) : Event