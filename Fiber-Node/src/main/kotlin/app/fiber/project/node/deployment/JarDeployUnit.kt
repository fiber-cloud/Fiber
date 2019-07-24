package app.fiber.project.node.deployment

import app.fiber.project.node.deployment.lifecycle.LifeCycle
import app.fiber.project.node.deployment.profile.DeploymentProfile
import java.nio.file.Path

data class JarDeployUnit(
    val jarFile: Path,
    val workingDirectory: Path,
    val startParameters: List<String>,
    val deploymentProfile: DeploymentProfile,
    val priority: Int = 0,
    var lifeCycle: LifeCycle = LifeCycle.OFFLINE
) : Comparable<JarDeployUnit> {

    override fun compareTo(other: JarDeployUnit): Int = other.priority.compareTo(this.priority)

}