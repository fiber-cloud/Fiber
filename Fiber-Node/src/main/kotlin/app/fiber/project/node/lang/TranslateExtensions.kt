package app.fiber.project.node.lang

fun String.translate(): String {
    return LanguageManager.language?.properties?.getProperty(this)?: this
}