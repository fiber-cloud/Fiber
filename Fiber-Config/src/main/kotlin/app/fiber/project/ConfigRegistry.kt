package app.fiber.project

object ConfigRegistry {

    private val files = mutableListOf<ConfigFile>()

    fun init() {
        this.loadFiles()
        Runtime.getRuntime().addShutdownHook(Thread(this::saveFiles))
    }

    fun register(vararg files: ConfigFile) = this.files.addAll(files)

    private fun loadFiles() = this.files.forEach { it.load() }

    private fun saveFiles() = this.files.forEach { it.save() }

}