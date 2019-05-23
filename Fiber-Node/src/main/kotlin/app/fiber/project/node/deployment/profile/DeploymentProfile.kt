package app.fiber.project.node.deployment.profile

data class DeploymentProfile(
    val name: String,
    var initialMemorySize: Int = 256,
    var maximalMemorySize: Int = 512,
    var memoryUnit: MemoryUnit = MemoryUnit.MEGABYTE,
    val systemProperties: MutableMap<String, String> = mutableMapOf(),
    val environmentVariables: MutableMap<String, String> = mutableMapOf()
)