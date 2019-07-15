package app.fiber.project.node.event

import kotlin.reflect.KClass

typealias EventListener = (Event) -> Unit

class EventBus {
    val subscribers = mutableMapOf<KClass<*>, MutableList<EventListener>>()

    inline fun <reified T : Event> subscribe(noinline body: (it: T) -> Unit) {
        if (subscribers[T::class] == null) subscribers[T::class] = mutableListOf()
        subscribers[T::class]!!.add(body as EventListener)
    }

    fun fire(event: Event) = subscribers[event::class]?.forEach { it(event) }
}