package app.fiber.project.node.deployment.impl.process

import app.fiber.project.node.deployment.JarDeployable
import app.fiber.project.node.deployment.Deployer
import app.fiber.project.node.deployment.event.PreDeployEvent
import app.fiber.project.node.deployment.impl.process.result.ProcessDeployResult
import app.fiber.project.node.deployment.result.DeployResult
import app.fiber.project.node.event.EventBus
import app.fiber.project.node.logging.Logger
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.nio.file.Files

class ProcessDeployer : Deployer, KoinComponent {

    private val logger by inject<Logger>()

    private val eventBus by inject<EventBus>()

    //TODO coroutines
    override fun deploy(jarDeployable: JarDeployable): DeployResult {
        val startTime = System.currentTimeMillis()

        if (Files.notExists(jarDeployable.workingDirectory)) Files.createDirectories(jarDeployable.workingDirectory)

        this.eventBus.fire(PreDeployEvent(jarDeployable))

        val startCommand = this.buildStartCommand(jarDeployable)

        this.logger.debug(startCommand.toString())

        val processBuilder = ProcessBuilder().apply {
            this.command(startCommand)
            this.directory(jarDeployable.workingDirectory.toFile())
        }

        val process: Process? = try {
            processBuilder.start()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return ProcessDeployResult(process!!, System.currentTimeMillis() - startTime)
    }

    override fun isAvailable() = true

    private fun buildStartCommand(jarDeployable: JarDeployable): List<String> {
        val deployProfile = jarDeployable.deployProfile
        val startCommand = mutableListOf(
            "java",
            "-Xms${deployProfile.initialMemorySize}${deployProfile.memoryUnit.marker}",
            "-Xmx${deployProfile.maximalMemorySize}${deployProfile.memoryUnit.marker}"
        )

        deployProfile.systemProperties.forEach { startCommand.add("-D$it") }

        startCommand.addAll(listOf(
            "-jar",
            jarDeployable.jarFile.toString()
        ))

        startCommand.addAll(jarDeployable.startParameters)

        return startCommand
    }

}