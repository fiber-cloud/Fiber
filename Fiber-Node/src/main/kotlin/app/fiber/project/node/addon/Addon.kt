package app.fiber.project.node.addon

import app.fiber.project.node.addon.model.AddonInfo

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