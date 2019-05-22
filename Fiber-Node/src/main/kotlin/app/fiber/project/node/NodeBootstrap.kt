package app.fiber.project.node

import app.fiber.project.node.injection.nodeModule
import app.fiber.project.node.lang.LanguageManager
import app.fiber.project.node.logging.LogLevel
import app.fiber.project.node.logging.Logger
import app.fiber.project.node.misc.SecurityChecker
import joptsimple.OptionParser
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

fun main(args: Array<String>) = NodeBootstrap.launch(args)

object NodeBootstrap : KoinComponent {

    fun launch(args: Array<String>) {
        startKoin {
            modules(nodeModule)
        }

        LanguageManager.loadLanguage()

        val parser = OptionParser()
        parser.allowsUnrecognizedOptions()

        parser.acceptsAll(listOf("h", "help"), "Prints help")
        parser.acceptsAll(listOf("v", "version"), "Prints current version")
        parser.accepts("time", "Prints startup time")
        parser.accepts("level", "Sets the logger level").withRequiredArg()
        parser.accepts("allow-root", "Allows usage as root user. Use for debugging only !")

        val optionSet = parser.parse(*args)

        if (optionSet.has("level")) {
            val level: LogLevel? = LogLevel.valueOf(optionSet.valueOf("level").toString().toUpperCase())
            inject<Logger>().value.logLevel = level ?: LogLevel.INFO
        }

        if (optionSet.has("h") || optionSet.has("help")) {
            parser.printHelpOn(System.out)
            return
        }

        if (optionSet.has("v") || optionSet.has("version")) {
            val logger by inject<Logger>()

            logger.info("Current version: ${Node.version()}", false)
            return
        }

        if (!optionSet.has("allow-root")) SecurityChecker.checkRoot()

        Runtime.getRuntime().addShutdownHook(Thread(Node::stop))
        Node.start()
    }

}