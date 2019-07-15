package app.fiber.project.node.addon

interface Addon {
    /**
     * Implement enable logic
     */
    fun onEnable()

    /**
     * Implement disable logic
     */
    fun onDisable()

    /**
     * Implement getter for AddonInfo
     */
    fun addonInfo(): AddonInfo
}