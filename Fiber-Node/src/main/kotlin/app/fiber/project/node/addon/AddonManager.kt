package app.fiber.project.node.addon

import app.fiber.project.node.logging.Logger
import java.io.File
import java.io.FileInputStream
import java.net.URLClassLoader
import java.nio.file.Files
import java.util.jar.JarInputStream
import java.util.jar.JarEntry
import java.util.Enumeration
import java.util.jar.JarFile


class AddonManager(private val logger: Logger) {
    private val addonFolder = File("addons")
    val addons = init()

    private fun init(): List<Addon> {
        if (!Files.exists(addonFolder.toPath())) Files.createDirectories(addonFolder.toPath())
        return addonFolder.listFiles()
            .filter { it.isFile }
            .filter { it.endsWith(".jar") }
            .map (this::loadJar)
            .toList()
    }

    private fun loadJar(file: File): Addon {
        logger.debug("Loading addon ${file.name}!")

        val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()))
        var mainClass: Class<*> = Unit::class.java

        val jf = JarFile(file)
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

        logger.debug("Loaded addon ${file.name} successfully!")

        //init main class of Addon
        return mainClass.getDeclaredConstructor().newInstance() as Addon
    }
}