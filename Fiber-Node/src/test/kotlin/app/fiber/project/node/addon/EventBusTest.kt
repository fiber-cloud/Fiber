package app.fiber.project.node.addon

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture

object EventBusTest {
    private data class MessageEvent(val message: String) : Event
    private val eventBus = EventBus()

    @Test
    fun `EventBus should fire event`() {
        val future = CompletableFuture<MessageEvent>()
        val messageEventInstance = MessageEvent("Hello World!")

        eventBus.subscribe<MessageEvent> { future.complete(it) }
        eventBus.fire(messageEventInstance)

        Assertions.assertEquals(messageEventInstance.message, future.get().message)
    }
}
