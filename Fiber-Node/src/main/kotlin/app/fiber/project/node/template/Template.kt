package app.fiber.project.node.template

import com.fasterxml.jackson.annotation.JsonProperty

data class Template (
    val name: String,
    val software: String,
    val plugins: List<String> = emptyList(),
    @JsonProperty("extends")
    val parent: String?,
    val data: List<String> = emptyList(),
    val paths: Map<String, String> = emptyMap()
)