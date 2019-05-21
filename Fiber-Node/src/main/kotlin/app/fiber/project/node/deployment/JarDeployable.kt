package app.fiber.project.node.deployment

import app.fiber.project.node.deployment.profile.DeployProfile
import java.nio.file.Path

data class JarDeployable(val jarFile: Path, val workingDirectory: Path, val startParameters: List<String>, val deployProfile: DeployProfile)