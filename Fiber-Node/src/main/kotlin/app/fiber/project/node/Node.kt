package app.fiber.project.node

object Node {

    fun start() {

    }

    fun stop() {

    }

    fun version() = this.javaClass.`package`.implementationVersion ?: "dev version"

}