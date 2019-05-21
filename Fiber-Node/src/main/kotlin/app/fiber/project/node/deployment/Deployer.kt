package app.fiber.project.node.deployment

import app.fiber.project.node.deployment.result.DeployResult

interface Deployer {

    fun deploy(jarDeployable: JarDeployable): DeployResult

    fun isAvailable(): Boolean

}