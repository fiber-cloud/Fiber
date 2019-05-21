package app.fiber.project.node.injection

import app.fiber.project.node.addon.AddonManager
import app.fiber.project.node.event.EventBus
import app.fiber.project.node.logging.Logger
import app.fiber.project.node.software.proxy.ProxyGroupRegistry
import app.fiber.project.node.software.server.ServerGroupRegistry
import org.koin.dsl.module

val nodeModule = module {
    single { Logger() }
    single { AddonManager(get()) }
    single { EventBus() }
    single { ProxyGroupRegistry() }
    single { ServerGroupRegistry() }
}