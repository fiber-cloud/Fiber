package app.fiber.project.node.logging.file

import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.CompressionMethod
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class FileLogger {

    private val logDirectory = Paths.get("logs")

    private val latest = Paths.get("logs/latest.log")

    init {
        if (Files.exists(this.latest)) {
            this.archiveLatestLog()
            Files.delete(this.latest)
        }
    }

    fun log(message: String) {
        if (Files.notExists(this.logDirectory)) Files.createDirectories(this.logDirectory)

        if(Files.notExists(this.latest)) Files.createFile(this.latest)

        Files.newBufferedWriter(this.latest, StandardOpenOption.APPEND).use {
            it.write(message)
        }
    }

    fun clearLog() = this.logDirectory.toFile().listFiles()?.forEach { Files.delete(it.toPath()) }

    private fun archiveLatestLog() = ZipFile(this.nameForZip()).addFile(this.latest.toFile(), ZipParameters().apply {
        this.compressionMethod = CompressionMethod.DEFLATE
        this.compressionLevel = CompressionLevel.NORMAL
    })

    private fun nameForZip(): String {
        val millis = Files.getLastModifiedTime(this.latest).toMillis()
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime()
        return "logs/${DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm").format(date)}.log.zip"
    }

}