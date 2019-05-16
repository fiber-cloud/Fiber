dependencies {
    implementation(Dependencies.kotlinStandard)
}

tasks {
    val collectDependencies by registering(DependencyTask::class) {
        this.group = "fiber"
        this.description = "Collect all dependencies for the runtime in the dependencies.json file"
    }

    this.findByName("build")!!.dependsOn(collectDependencies)
}