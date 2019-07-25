package app.fiber.project.command.executor

import app.fiber.project.command.result.CommandResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandExecutorTest {

    @BeforeAll
    fun composeCommand() = CommandExecutor.register(ExecutorTestCommand::class)

    @Test
    fun `test default route`() {
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("Executor"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("alias"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("Alias"))
        assertEquals(CommandResult.COMMAND_NOT_FOUND, CommandExecutor.execute("Label"))
    }

    @Test
    fun `test sub commands`() {
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor sub"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor Sub"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("Executor Sub"))
        assertEquals(CommandResult.COMMAND_NOT_FOUND, CommandExecutor.execute("Label Sub"))
    }

    @Test
    fun `test routing`() {
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor route"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor Route"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("Executor Route"))
        assertEquals(CommandResult.ROUTE_NOT_FOUND, CommandExecutor.execute("Executor AnotherRoute"))
    }

    @Test
    fun `test parameter execution`() {
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor parameters string 0"))
        assertEquals(CommandResult.WRONG_PARAMETERS, CommandExecutor.execute("executor parameters string"))
    }

    @Test
    fun `test optional parameters`() {
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor optional string 0"))
        assertEquals(CommandResult.SUCCESS, CommandExecutor.execute("executor optional string"))
        assertEquals(CommandResult.WRONG_PARAMETERS, CommandExecutor.execute("executor optional"))
        assertEquals(CommandResult.WRONG_PARAMETERS, CommandExecutor.execute("executor optional string 0 anotherParam"))
    }

}