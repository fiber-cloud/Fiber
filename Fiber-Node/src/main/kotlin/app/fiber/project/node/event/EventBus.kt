package app.fiber.project.node.event

import kotlin.reflect.KClass

class EventBus {
    val subscribers = mutableMapOf<KClass<*>, MutableList<(Event) -> Unit>>()

    inline fun <reified T : Event> subscribe(noinline body: (it: T) -> Unit) {
        if (subscribers[T::class] == null) subscribers[T::class] = mutableListOf()
        subscribers[T::class]!!.add(body as (Event) -> Unit)
    }

    fun fire(event: Event) = subscribers[event::class]?.forEach { it(event) }
}