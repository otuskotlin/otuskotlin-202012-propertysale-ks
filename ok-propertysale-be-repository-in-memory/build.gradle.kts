plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val cache2kVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":ok-propertysale-be-common"))

    implementation(kotlin("stdlib"))
    implementation("org.cache2k:cache2k-core:$cache2kVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
