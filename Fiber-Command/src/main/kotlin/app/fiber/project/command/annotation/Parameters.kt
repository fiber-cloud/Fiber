package app.fiber.project.command.annotation

/**
 * Declares an array of [Parameters][Parameter].
 *
 * @property [value] Array of all [Parameter].
 *
 * @author Tammo0987
 * @since 1.0
 */
@Retention
@Target(AnnotationTarget.FUNCTION)
annotation class Parameters(val value: Array<Parameter>)