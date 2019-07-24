package app.fiber.project.node.deployment.strategy

import app.fiber.project.node.deployment.JarDeployUnit

interface DeployStrategy {

    fun collectDeployUnits(): Collection<JarDeployUnit>

}