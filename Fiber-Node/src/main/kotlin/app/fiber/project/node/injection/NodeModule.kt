package app.fiber.project.node.injection

import app.fiber.project.node.addon.AddonManager
import app.fiber.project.node.logging.Logger
import org.koin.dsl.module

val nodeModule = module {
    single { Logger() }
    single { AddonManager(get()) }
}