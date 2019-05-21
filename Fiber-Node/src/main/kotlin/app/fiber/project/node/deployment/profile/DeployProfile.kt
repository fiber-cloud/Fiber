package app.fiber.project.node.deployment.profile

data class DeployProfile(
    val name: String,
    var initialMemorySize: Int = 256,
    var maximalMemorySize: Int = 512,
    var memoryUnit: MemoryUnit = MemoryUnit.MEGABYTE,
    val systemProperties: MutableList<String> = mutableListOf(),
    val environmentVariables: MutableMap<String, String> = mutableMapOf()
)