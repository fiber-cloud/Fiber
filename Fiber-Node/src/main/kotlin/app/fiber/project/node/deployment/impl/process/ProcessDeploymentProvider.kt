package app.fiber.project.node.deployment.impl.process

import app.fiber.project.node.deployment.JarDeployUnit
import app.fiber.project.node.deployment.DeploymentProvider
import app.fiber.project.node.deployment.event.PostDeploymentEvent
import app.fiber.project.node.deployment.event.PreDeploymentEvent
import app.fiber.project.node.deployment.impl.process.result.ProcessDeploymentResult
import app.fiber.project.node.deployment.result.DeploymentResult
import app.fiber.project.node.addon.EventBus
import app.fiber.project.node.logging.Logger
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.nio.file.Files

class ProcessDeploymentProvider : DeploymentProvider, KoinComponent {

    private val logger by inject<Logger>()

    private val eventBus by inject<EventBus>()

    //TODO coroutines
    override fun deploy(jarDeployUnit: JarDeployUnit): DeploymentResult {
        val startTime = System.currentTimeMillis()

        if (Files.notExists(jarDeployUnit.workingDirectory)) Files.createDirectories(jarDeployUnit.workingDirectory)

        this.eventBus.fire(PreDeploymentEvent(jarDeployUnit))

        val startCommand = this.buildStartCommand(jarDeployUnit)

        this.logger.debug(startCommand.toString())

        val processBuilder = ProcessBuilder().apply {
            this.command(startCommand)
            this.directory(jarDeployUnit.workingDirectory.toFile())
            this.environment().putAll(jarDeployUnit.deploymentProfile.environmentVariables)
        }

        val process: Process? = try {
            processBuilder.start()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        val result = ProcessDeploymentResult(process!!, System.currentTimeMillis() - startTime)

        this.eventBus.fire(PostDeploymentEvent(result))

        return result
    }

    override fun isAvailable() = true

    private fun buildStartCommand(jarDeployUnit: JarDeployUnit): List<String> {
        val deployProfile = jarDeployUnit.deploymentProfile
        val startCommand = mutableListOf(
            "java",
            "-Xms${deployProfile.initialMemorySize}${deployProfile.memoryUnit.marker}",
            "-Xmx${deployProfile.maximalMemorySize}${deployProfile.memoryUnit.marker}"
        )

        deployProfile.systemProperties.forEach { startCommand.add("-D${it.key}=${it.value}") }

        startCommand.addAll(listOf(
            "-jar",
            jarDeployUnit.jarFile.toString()
        ))

        startCommand.addAll(jarDeployUnit.startParameters)

        return startCommand
    }

}