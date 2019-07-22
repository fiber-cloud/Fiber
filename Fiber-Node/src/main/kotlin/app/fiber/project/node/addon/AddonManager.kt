package app.fiber.project.node.addon

import app.fiber.project.node.logging.Logger
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import java.util.jar.JarFile


class AddonManager : KoinComponent {
    private val logger by inject<Logger>()

    private val addonFolder = File("addons")
    internal val addons = addonFolder.listFiles()
        .filter { it.isFile }
        .filter { it.endsWith(".jar") }
        .map(this::loadJar)
        .toList()

    init {
        if (!Files.exists(addonFolder.toPath())) Files.createDirectories(addonFolder.toPath())
    }

    private fun loadJar(file: File): Addon {
        with(file) {
            logger.debug("Loading addon $name!")
            val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()))
            var mainClass: Class<*> = Unit::class.java

            val jf = JarFile(this)
            val en = jf.entries()
            while (en.hasMoreElements()) {
                val je = en.nextElement()
                if (je.name.endsWith(".class")) {
                    val clazz = classLoader.loadClass(je.name.replace("/", ".").substring(0, je.name.length - 6))
                    if (clazz.interfaces.contains(Addon::class.java)) {
                        mainClass = clazz
                    }
                }
            }

            logger.debug("Loaded addon $name successfully!")

            //init main class of Addon
            val instance = mainClass.getDeclaredConstructor().newInstance() as Addon
            instance.onEnable()
            return instance
        }
    }
}