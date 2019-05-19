package app.fiber.project.node

import app.fiber.project.node.logging.Logger
import app.fiber.project.node.logging.asciiArt
import app.fiber.project.node.pipeline.impl.StartPipeline
import org.koin.core.KoinComponent
import org.koin.core.inject

object Node : KoinComponent {

    private val logger by inject<Logger>()

    fun start() {
        this.logger.info("Starting node...", false)

        asciiArt.split("\n").forEach { this.logger.info(it) }

        StartPipeline().execute()
    }

    fun stop() {

    }

    fun version() = this.javaClass.`package`.implementationVersion ?: "dev version"

}