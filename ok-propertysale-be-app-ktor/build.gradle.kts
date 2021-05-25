plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

group = rootProject.group
version = rootProject.version

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

docker {
    javaApplication {
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("(c) Otus")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms512m", "-Xmx512m"))
    }
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    val ktorVersion: String by project
    val kotlinVersion: String by project
    val logbackVersion: String by project
    val serializationVersion: String by project
    val ktorRabbitmqFeature: String by project
    val rabbitmqVersion: String by project
    val testContainersVersion: String by project

    implementation(project(":ok-propertysale-mp-common"))
    implementation(project(":ok-propertysale-be-common"))
    implementation(project(":ok-propertysale-mp-transport-ps"))
    implementation(project(":ok-propertysale-be-mappers-ps"))
    implementation(project(":ok-propertysale-be-business-logic"))
    implementation(project(":ok-propertysale-be-repository-cassandra"))
    implementation(project(":ok-propertysale-be-repository-in-memory"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    implementation("com.github.JUtupe:ktor-rabbitmq:$ktorRabbitmqFeature")
    implementation("com.rabbitmq:amqp-client:$rabbitmqVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
}
