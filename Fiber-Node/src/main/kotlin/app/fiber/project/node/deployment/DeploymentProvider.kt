package app.fiber.project.node.deployment

import app.fiber.project.node.deployment.result.DeploymentResult

interface DeploymentProvider {

    fun deploy(jarDeployUnit: JarDeployUnit): DeploymentResult

    fun isAvailable(): Boolean

}