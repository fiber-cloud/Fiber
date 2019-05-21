package app.fiber.project.node.command.converter

import app.fiber.project.command.converter.InputConverter
import app.fiber.project.node.software.server.ServerType

class ServerTypeConverter : InputConverter<ServerType> {

    override fun convert(input: String)= ServerType.valueOf(input.toUpperCase())

    override fun compatibleTypes() = listOf(ServerType::class)

}