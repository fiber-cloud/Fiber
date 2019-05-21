package app.fiber.project.node.lang

fun String.translate() = LanguageManager.language?.properties?.getProperty(this)?: this