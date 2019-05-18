package app.fiber.project.node.addon

import app.fiber.project.node.logging.Logger
import org.jetbrains.kotlin.cli.common.environment.setIdeaIoUseFallback
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class AddonManager(private val logger: Logger) {
    val addons = mutableListOf<Addon>()
    private var ktsScriptEngine: ScriptEngine = ScriptEngineManager().getEngineByExtension("kts")

    fun init() {
        logger.info("Loading Addons")
        setIdeaIoUseFallback()

        val addonFolder = File("addons")
        if (!Files.exists(addonFolder.toPath())) Files.createDirectories(addonFolder.toPath())
        addonFolder.listFiles().forEach {
            if (it.isFile && it.name.endsWith(".jar")) {
                TODO("Open jar and load classes")
            } else if (it.isDirectory) {
                val mainFile = File(it, "main.kts")
                if (mainFile.exists()) {
                    //Launch
                    logger.debug("Launching main.kts of ${it.absolutePath}")
                    ktsScriptEngine.eval(FileReader(mainFile))
                }
            }
        }
        logger.info("Addons loaded successfully")
    }
}