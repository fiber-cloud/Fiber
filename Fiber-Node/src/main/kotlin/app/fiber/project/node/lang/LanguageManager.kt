package app.fiber.project.node.lang

import java.util.*

object LanguageManager {

    var language: Language? = null

    fun loadLanguage(language: String = this.getLanguage()) {
        this.language = Language(Properties()
            .also { it.load(Thread.currentThread().contextClassLoader.getResourceAsStream("lang/$language.lang")!!) }
        )
    }

    private fun getLanguage(): String = Locale.getDefault().language ?: Locale.ENGLISH.language

}