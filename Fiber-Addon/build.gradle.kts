repositories {
    jcenter()
}

dependencies {
    implementation(Dependencies.kotlinStandard)
    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.kotlinCompilerEmbeddable)
    implementation(Dependencies.kotlinScriptUtil)
    implementation(Dependencies.kotlinScriptingCompilerEmbeddable)
    implementation(Dependencies.kotlinScriptRuntime)

    implementation(Dependencies.koin)
}