package app.fiber.project.node.config

import app.fiber.project.ConfigFile
import app.fiber.project.node.deployment.profile.DeploymentProfileRegistry
import app.fiber.project.yaml.fromYaml
import app.fiber.project.yaml.toYaml
import org.koin.core.KoinComponent
import org.koin.core.inject

class DeploymentProfileConfig : ConfigFile("deployment_profiles"), KoinComponent {

    override fun load() {
        /*
         * Can not declared as class property in the current implementation,
         * because save is called before the constructor of this class is called.
         */
        val deploymentProfileRegistry by inject<DeploymentProfileRegistry>()

        deploymentProfileRegistry.deploymentProfiles = fromYaml(this.path)
    }

    override fun save() {
        val deploymentProfileRegistry by inject<DeploymentProfileRegistry>()

        deploymentProfileRegistry.deploymentProfiles.toYaml(this.path)
    }

}