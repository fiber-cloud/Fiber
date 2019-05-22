package app.fiber.project.node.lang

import java.text.MessageFormat
import java.util.*

data class Language(private val properties: Properties) {
    fun translate(identifier: String, args: Array<out String>): String {
        val formatter = MessageFormat(properties.getProperty(identifier))
        return formatter.format(args)
    }
}