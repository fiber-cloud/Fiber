package app.fiber.project.yaml

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Path

val MAPPER = ObjectMapper(
    YAMLFactory()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
    .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
    .registerModule(KotlinModule())!!

fun Any.toYaml(path: Path) = MAPPER.writeValue(path.toFile(), this)

inline fun <reified T> fromYaml(path: Path): T = MAPPER.readValue(path.toFile(), T::class.java)