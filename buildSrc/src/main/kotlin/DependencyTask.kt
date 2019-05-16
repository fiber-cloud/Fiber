import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class DependencyTask : DefaultTask() {

    @TaskAction
    fun collectAndPrint() {
        val dependencies = this.project.subprojects
            .asSequence()
            .map { it.configurations }
            .flatten()
            .filter { it.name.endsWith("implementation") }
            .map { it.dependencies }
            .flatten()
            .map { Dependency(it.group!!, it.name, it.version!!) }
            .toList()
            .distinct()

        val mapper = jacksonObjectMapper()
        val file = File("Fiber-Node/src/main/resources/dependencies.json")
        file.printWriter().use {
            it.write(mapper.writeValueAsString(dependencies))
        }
    }

}

data class Dependency(val group: String, val name: String, val version: String)