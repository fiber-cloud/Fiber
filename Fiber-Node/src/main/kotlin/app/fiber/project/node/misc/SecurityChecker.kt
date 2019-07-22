package app.fiber.project.node.misc

import java.nio.file.Files
import java.nio.file.Paths

object SecurityChecker {

    /**
     * Check for root on unix, but ignores Docker
     */
    fun checkRoot() {
        //check if operating system is unix
        if (!System.getProperty("os.name").startsWith("Windows")) {
            //check if in container; because container is always root
            val containerRootFile = Paths.get("/root.fibercloud")
            if (!Files.exists(containerRootFile))  {
                //the docker file doesn't exist; root check
                val user = Runtime.getRuntime().exec("whoami").inputStream.reader().readText().trim()
                if (user == "root") {
                    throw RuntimeException("Fiber shouldn't be executed as the root user!")
                }
            }
        }
    }
}