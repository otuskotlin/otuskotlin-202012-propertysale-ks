plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val cassandraVersion: String by project
    val coroutinesVersion: String by project
    val testContainersVersion: String by project

    implementation(project(":ok-propertysale-be-common"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("com.datastax.oss:java-driver-core:$cassandraVersion")
    implementation("com.datastax.oss:java-driver-query-builder:$cassandraVersion")
    kapt("com.datastax.oss:java-driver-mapper-processor:$cassandraVersion")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$cassandraVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:$coroutinesVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
}
