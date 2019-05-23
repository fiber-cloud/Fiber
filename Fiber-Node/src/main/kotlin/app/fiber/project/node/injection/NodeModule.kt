package app.fiber.project.node.injection

import app.fiber.project.node.addon.AddonManager
import app.fiber.project.node.deployment.profile.DeploymentProfileRegistry
import app.fiber.project.node.event.EventBus
import app.fiber.project.node.logging.Logger
import app.fiber.project.node.software.ProxyGroupRegistry
import app.fiber.project.node.software.ServerGroupRegistry
import app.fiber.project.node.template.TemplateRegistry
import org.koin.dsl.module

val nodeModule = module {
    single { Logger() }
    single { AddonManager(get()) }
    single { EventBus() }
    single { ProxyGroupRegistry() }
    single { ServerGroupRegistry() }
    single { TemplateRegistry() }
    single { DeploymentProfileRegistry() }
}