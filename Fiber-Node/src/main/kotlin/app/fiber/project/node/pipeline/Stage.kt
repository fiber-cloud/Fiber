package app.fiber.project.node.pipeline

@Retention
@Target(AnnotationTarget.FUNCTION)
annotation class Stage(val step: Int)