pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val springDependencyVersion: String by settings
        val springBootVersion: String by settings
        val bmuschkoVersion: String by settings
        val kotlessVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        kotlin("plugin.spring") version kotlinVersion apply false
        kotlin("plugin.allopen") version kotlinVersion apply false

        id("org.springframework.boot") version springBootVersion apply false
        id("io.spring.dependency-management") version springDependencyVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false

        id("io.kotless") version kotlessVersion apply false
    }
}

rootProject.name = "otuskotlin-202012-propertysale-ks"

include(
    "ok-propertysale-be-app-kotless",
    "ok-propertysale-be-app-ktor",
    "ok-propertysale-be-app-spring",
    "ok-propertysale-be-app-spring-fu",
    "ok-propertysale-be-business-logic",
    "ok-propertysale-be-common",
    "ok-propertysale-be-mappers-ps",
    "ok-propertysale-fe-common",
    "ok-propertysale-mp-common",
    "ok-propertysale-mp-pipelines",
    "ok-propertysale-mp-pipelines-validation",
    "ok-propertysale-mp-transport-ps"
)
