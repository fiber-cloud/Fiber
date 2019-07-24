package app.fiber.project.node.command

import app.fiber.project.command.annotation.Model
import app.fiber.project.command.annotation.Parameter
import app.fiber.project.command.annotation.Parameters
import app.fiber.project.command.annotation.Route
import app.fiber.project.command.result.CommandResult
import app.fiber.project.node.logging.Logger
import app.fiber.project.node.software.ProxyGroupRegistry
import app.fiber.project.node.software.ServerGroupRegistry
import app.fiber.project.node.software.proxy.ProxyGroup
import app.fiber.project.node.software.server.ServerGroup
import app.fiber.project.node.software.server.ServerType
import app.fiber.project.node.template.Template
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

@Model("group", "Manage server and proxy groups", subModels = [ProxyCommand::class, ServerCommand::class])
class GroupCommand

@Model("proxy", "Manager proxy groups")
class ProxyCommand : KoinComponent {

    private val logger by inject<Logger>()

    private val proxyRegistry by inject<ProxyGroupRegistry>()

    @Route("add", "Adds a proxy group")
    @Parameters(
        [
            Parameter("name", "Name of the proxy group"),
            Parameter("instances", "Range of instances of the proxy group", IntRange::class),
            Parameter("template", "Template of the proxy group"),
            Parameter("fallback", "Fallback server group", optional = true)
        ]
    )
    fun addGroup(name: String, instances: IntRange, template: String, fallback: String?): CommandResult {
        val proxyGroup = ProxyGroup(name, UUID.randomUUID(), instances, template, fallback)

        this.proxyRegistry.add(proxyGroup)
        this.logger.info("Added proxy group $name")

        return CommandResult.SUCCESS
    }

    @Route("remove", "Removes a proxy group")
    @Parameter("name", "Name of the proxy group")
    fun removeGroup(name: String): CommandResult {
        val proxyGroup = this.proxyRegistry.findByName(name)

        if (proxyGroup == null) {
            this.logger.warn("Proxy group with name $name does not exist")
            return CommandResult.FAILED
        }

        this.proxyRegistry.remove(proxyGroup)
        this.logger.info("Removed proxy group $name")

        return CommandResult.SUCCESS
    }

    @Route("list", "List all proxy groups")
    fun listGroups(): CommandResult {
        if (this.proxyRegistry.groups.isEmpty()) {
            this.logger.info("There are no proxy groups")
            return CommandResult.SUCCESS
        }

        this.logger.info("<-- Proxy Groups -->")
        this.logger.info("")

        this.proxyRegistry.groups.forEachIndexed { index, it ->
            this.logger.info(
                "${index + 1}: Name=${it.name}, Template=${it.template}"
            )
        }
        this.logger.info("")

        return CommandResult.SUCCESS
    }

}

@Model("server", "Manage server groups")
class ServerCommand : KoinComponent {

    private val logger by inject<Logger>()

    private val serverRegistry by inject<ServerGroupRegistry>()

    @Route("add", "Adds a server group")
    @Parameters(
        [
            Parameter("name", "Name of the server group"),
            Parameter("instances", "Range of instances of the server group", IntRange::class),
            Parameter("template", "Template of the proxy group"),
            Parameter("priority", "Priority to start the group", Int::class),
            Parameter("type", "Type of the server", ServerType::class)
        ]
    )
    fun addGroup(
        name: String,
        instances: IntRange,
        template: String,
        priority: Int,
        type: ServerType
    ): CommandResult {
        val serverGroup = ServerGroup(name, UUID.randomUUID(), instances, template, priority, type)

        this.serverRegistry.add(serverGroup)
        this.logger.info("Added server group $name")

        return CommandResult.SUCCESS
    }

    @Route("remove", "Removes a server group")
    @Parameter("name", "Name of the server group")
    fun removeGroup(name: String): CommandResult {
        val serverGroup = this.serverRegistry.findByName(name)

        if (serverGroup == null) {
            this.logger.warn("Server group with name $name does not exist")
            return CommandResult.FAILED
        }

        this.serverRegistry.remove(serverGroup)
        this.logger.info("Removed server group $name")

        return CommandResult.SUCCESS
    }

    @Route("list", "List all server groups")
    fun listGroups(): CommandResult {
        if (this.serverRegistry.groups.isEmpty()) {
            this.logger.info("There are no server groups")
            return CommandResult.SUCCESS
        }

        this.logger.info("<-- Server Groups -->")
        this.logger.info("")

        this.serverRegistry.groups.forEachIndexed { index, it ->
            this.logger.info(
                "${index + 1}: Name=${it.name}, Priority=${it.priority}, Template=${it.template}"
            )
        }
        this.logger.info("")

        return CommandResult.SUCCESS
    }

}