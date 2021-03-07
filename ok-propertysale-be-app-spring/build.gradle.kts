plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

group = rootProject.group
version = rootProject.group

val javaVersion: String by project

java.sourceCompatibility = JavaVersion.toVersion(javaVersion)
java.targetCompatibility = JavaVersion.toVersion(javaVersion)

dependencies {
    val serializationVersion: String by project

    implementation(project(":ok-propertysale-mp-common"))
    implementation(project(":ok-propertysale-mp-transport-ps"))
    implementation(project(":ok-propertysale-be-common"))
    implementation(project(":ok-propertysale-be-mappers-ps"))

    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    bootBuildImage {
        imageName = "ok-propertysale-be-app-spring:${rootProject.version}"
    }
}
