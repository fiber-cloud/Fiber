package app.fiber.project.node.pipeline.impl

import app.fiber.project.node.addon.AddonManager
import app.fiber.project.node.pipeline.Pipeline
import app.fiber.project.node.pipeline.Stage
import org.koin.core.KoinComponent
import org.koin.core.inject

class StartPipeline : Pipeline, KoinComponent {

    private val addonManager by inject<AddonManager>()

    @Stage(0)
    fun initAddonManager() = this.addonManager.init()

}
