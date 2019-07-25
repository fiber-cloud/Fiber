package app.fiber.project.command.composer

import app.fiber.project.command.TestCommand
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandComposerTest {

    private val testCommand = CommandModelComposer.compose(TestCommand::class)

    @Nested
    inner class ModelComposerTest {

        private val testCommand = this@CommandComposerTest.testCommand

        @Test
        fun `test label`() = assertEquals("test", this.testCommand.label)

        @Test
        fun `test aliases`() {
            assertEquals(1, this.testCommand.aliases.size)
            assertEquals("alias", this.testCommand.aliases.elementAt(0))
        }

        @Test
        fun `test sub commands`() {
            assertEquals(1, this.testCommand.subModels.size)
            assertEquals("sub", this.testCommand.subModels.elementAt(0).label)
        }

    }

    @Nested
    inner class RouteComposerTest {

        @Test
        fun `test routes`() = assertEquals(2, this@CommandComposerTest.testCommand.routes.size)

        @Test
        fun `test sub command routes`() = assertEquals(2, this@CommandComposerTest.testCommand.subModels.elementAt(0).routes.size)

    }

    @Nested
    inner class ParameterComposerTest {

        private val parameters = this@CommandComposerTest.testCommand.routes.find { it.name == "parameter"}!!.parameters

        @Test
        fun `test parameters`() {
            assertEquals(2, this.parameters.size)

            val parameter = this.parameters.find { it.name == "test" }!!

            assertEquals(String::class, parameter.type)
            assertFalse(parameter.optional)

            val optionalParameter = this.parameters.find { it.name == "optional" }!!

            assertEquals(String::class, optionalParameter.type)
            assertTrue(optionalParameter.optional)
        }

    }

}