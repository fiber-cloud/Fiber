plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

dependencies {
    val ktorVersion = "1.2.0"
    
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
}