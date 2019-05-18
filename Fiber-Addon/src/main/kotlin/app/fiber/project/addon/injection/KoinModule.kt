package app.fiber.project.addon.injection

import app.fiber.project.addon.AddonManager
import org.koin.dsl.module

val addonModule = module {
    single { AddonManager() }
}