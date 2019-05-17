package app.fiber.project.node.logging

enum class LogLevel {

    DEBUG,
    INFO,
    WARNING,
    ERROR;

    override fun toString() = this.name.toLowerCase().capitalize()

}