package app.fiber.project.node.deployment.strategy.impl

import app.fiber.project.node.deployment.JarDeployUnit
import app.fiber.project.node.deployment.profile.DeploymentProfile
import app.fiber.project.node.deployment.strategy.DeployStrategy
import java.nio.file.Paths
import java.util.*

class DefaultDeployStrategy : DeployStrategy {

    //TODO Only a testing strategy. There will be a better and functional implementation soon.
    private var deployed = false

    override fun collectDeployUnits(): Collection<JarDeployUnit> {
        if (!this.deployed) {
            this.deployed = true
            return listOf(
                JarDeployUnit(
                    Paths.get("resources/softwares/spigot.jar").toAbsolutePath(),
                    Paths.get("instances/${UUID.randomUUID()}"),
                    listOf("-p", 25565.toString()),
                    DeploymentProfile("default"/*, systemProperties = mutableMapOf("com.mojang.eula.agree" to "true")*/),
                    25565
                ),
                JarDeployUnit(
                    Paths.get("resources/softwares/spigot.jar").toAbsolutePath(),
                    Paths.get("instances/${UUID.randomUUID()}"),
                    listOf("-p", 25566.toString()),
                    DeploymentProfile("default" /*systemProperties = mutableMapOf("com.mojang.eula.agree" to "true")*/),
                    25566
                )
            )
        } else {
            return emptyList()
        }
    }

}