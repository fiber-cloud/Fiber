package app.fiber.project.node.logging

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Logger {

    fun debug(message: String) = this.print(LogLevel.DEBUG, message)

    fun info(message: String, newLine: Boolean = true) = this.print(LogLevel.INFO, message, newLine)

    fun warn(message: String) = this.print(LogLevel.WARNING, message)

    fun error(message: String) = this.print(LogLevel.ERROR, message)

    private fun print(level: LogLevel, message: String, newLine: Boolean = true) {
        println(this.formatLog(level, message))
        if(newLine) this.printPrefix()
    }

    private fun formatLog(level: LogLevel, message: String): String {
        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        return "\r[$level] [$time] $message"
    }

    private fun printPrefix() = println("\r>")

    //TODO files

}