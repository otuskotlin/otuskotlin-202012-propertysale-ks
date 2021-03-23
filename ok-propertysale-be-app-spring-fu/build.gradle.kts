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
    val springFuVersion: String by project
    val serializationVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":ok-propertysale-mp-common"))
    implementation(project(":ok-propertysale-be-common"))
    implementation(project(":ok-propertysale-mp-transport-ps"))
    implementation(project(":ok-propertysale-be-mappers-ps"))
    implementation(project(":ok-propertysale-be-business-logic"))

    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.fu:spring-fu-kofu:$springFuVersion")
    implementation("org.springframework:spring-webmvc")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
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
        imageName = "ok-propertysale-be-app-spring-fu:${rootProject.version}"
    }
}
