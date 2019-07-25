dependencies {
    implementation(Dependencies.kotlinStandard)
    implementation(Dependencies.kotlinReflect)

    testImplementation(TestDependencies.jUnit)
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}