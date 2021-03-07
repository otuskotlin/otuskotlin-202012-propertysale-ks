pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val springDependencyVersion: String by settings
        val springBootVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion
    }
}

rootProject.name = "otuskotlin-202012-propertysale-ks"

include(
    "ok-propertysale-be-app-spring",
    "ok-propertysale-be-app-spring-fu",
    "ok-propertysale-be-common",
    "ok-propertysale-be-mappers-ps",
    "ok-propertysale-fe-common",
    "ok-propertysale-mp-common",
    "ok-propertysale-mp-transport-ps"
)
