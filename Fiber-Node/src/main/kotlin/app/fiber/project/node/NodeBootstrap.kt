package app.fiber.project.node

import app.fiber.project.node.logging.Logger
import joptsimple.OptionParser

fun main(args: Array<String>) = NodeBootstrap.launch(args)

object NodeBootstrap {

    private val logger = Logger()

    fun launch(args: Array<String>) {
        val parser = OptionParser()
        parser.allowsUnrecognizedOptions()

        parser.acceptsAll(listOf("h", "help"), "Prints help")
        parser.acceptsAll(listOf("v", "version"), "Prints current version")
        parser.accepts("time", "Prints startup time")

        val optionSet = parser.parse(*args)

        if (optionSet.has("h") || optionSet.has("help")) {
            parser.printHelpOn(System.out)
            return
        }

        if (optionSet.has("v") || optionSet.has("version")) {
            this.logger.info("Current version: ${Node.version()}", false)
            return
        }

        Runtime.getRuntime().addShutdownHook(Thread(Node::stop))
        Node.start()
    }

}