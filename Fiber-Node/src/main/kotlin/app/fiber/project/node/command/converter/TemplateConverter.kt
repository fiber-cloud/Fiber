package app.fiber.project.node.command.converter

import app.fiber.project.command.converter.InputConverter
import app.fiber.project.node.template.Template
import app.fiber.project.node.template.TemplateRegistry
import org.koin.core.KoinComponent
import org.koin.core.inject

class TemplateConverter : InputConverter<Template>, KoinComponent {

    private val templateRegistry by inject<TemplateRegistry>()

    override fun convert(input: String) = this.templateRegistry.findByName(input)!!

    override fun compatibleTypes() = listOf(Template::class)

}