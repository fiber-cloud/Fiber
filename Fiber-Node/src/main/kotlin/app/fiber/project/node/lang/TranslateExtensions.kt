package app.fiber.project.node.lang

fun String.translate(vararg params: String) = LanguageManager.language?.translate(this, params)?: this