package app.fiber.project.node.template

import app.fiber.project.node.io.listFiles
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.koin.core.KoinComponent
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class TemplateRegistry : KoinComponent {

    private val mapper = ObjectMapper(YAMLFactory()).apply {
        this.registerModule(KotlinModule())
    }

    private val templates = mutableListOf<Template>()

    fun loadTemplates() {
        val templateDirectory = Paths.get("templates")
        if (Files.notExists(templateDirectory)) Files.createDirectories(templateDirectory)

        this.loadTemplates(templateDirectory)
        this.checkTemplates()
    }

    fun findByName(name: String) = this.templates.find { it.name == name }

    private fun loadTemplates(templateDirectory: Path) {
        templateDirectory.listFiles().forEach { this.loadTemplate(it) }
    }

    private fun loadTemplate(templatePath: Path) = this.templates.add(this.mapper.readValue(templatePath.toFile()))

    private fun checkTemplates() {
        this.templates.forEach {
            if (it.name == it.parent) {
                throw TemplateLoadException("Can not extend from itself")
            }

            if (it.parent != null && this.findByName(it.parent) == null) {
                throw TemplateLoadException("Parent template not found")
            }

            if (it.parent != null) {
                val name = it.name
                var parent = this.findByName(it.parent)
                while (parent!!.parent != null) {
                    if (parent.parent == name) {
                        throw TemplateLoadException("Cyclic dependencies")
                    } else {
                        parent = this.findByName(parent.parent!!)
                    }
                }
            }
        }
    }

}