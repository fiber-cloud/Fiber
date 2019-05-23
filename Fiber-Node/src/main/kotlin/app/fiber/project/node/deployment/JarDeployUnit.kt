package app.fiber.project.node.deployment

import app.fiber.project.node.deployment.profile.DeploymentProfile
import java.nio.file.Path

data class JarDeployUnit (
    val jarFile: Path,
    val workingDirectory: Path,
    val startParameters: List<String>,
    val deploymentProfile: DeploymentProfile
)