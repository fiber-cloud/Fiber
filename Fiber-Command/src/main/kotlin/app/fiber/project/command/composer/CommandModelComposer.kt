package app.fiber.project.command.composer

import app.fiber.project.command.CommandModel
import app.fiber.project.command.annotation.Model
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Composing a [CommandModel] from a class, which is declared as command with the [Model] annotation.
 *
 * @author Tammo0987
 * @since 1.0
 */
object CommandModelComposer {

    /**
     * Composing and scanning a [KClass] to create a [CommandModel] instance.
     *
     * @param [clazz] [KClass] to compose.
     *
     * @return [CommandModel] instance.
     */
    fun compose(clazz: KClass<*>): CommandModel {
        if (clazz.findAnnotation<Model>() == null) {
            throw IllegalStateException("${clazz.simpleName} does not declare an annotation of type Model")
        }

        val routes = CommandRouteComposer.compose(clazz)
        val model = clazz.findAnnotation<Model>()!!

        val aliases = model.aliases.toList()
        val subModels = model.subModels.map { compose(it) }

        return CommandModel(model.label, model.description, aliases, routes, subModels)
    }

}