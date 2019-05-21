package app.fiber.project.node.software

abstract class GroupRegistry<T : Group> {

    private val groups = mutableListOf<T>()

    fun add(group: T) = this.groups.add(group)

    fun remove(group: T) = this.groups.remove(group)

    fun findByName(name: String) = this.groups.find { it.name == name }

}