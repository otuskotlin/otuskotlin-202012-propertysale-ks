pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val springDependencyVersion: String by settings
        val springBootVersion: String by settings
        val bmuschkoVersion: String by settings
        val kotlessVersion: String by settings

        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion
        kotlin("kapt") version kotlinVersion

        kotlin("plugin.serialization") version kotlinVersion

        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyVersion

        id("com.bmuschko.docker-java-application") version bmuschkoVersion

        id("io.kotless") version kotlessVersion
    }
}

rootProject.name = "otuskotlin-202012-propertysale-ks"

include(
    // Приложения
    "ok-propertysale-be-app-kotless",
    "ok-propertysale-be-app-ktor",
    "ok-propertysale-be-app-spring",
    "ok-propertysale-be-app-spring-fu",

    // Бэкенд (JVM) подпроекты
    "ok-propertysale-be-business-logic",
    "ok-propertysale-be-common",
    "ok-propertysale-be-mappers-ps",
    "ok-propertysale-be-repository-in-memory",
    "ok-propertysale-be-repository-cassandra",
    "ok-propertysale-be-logging",

    // Фронтенд подпроекты
    "ok-propertysale-fe-common",

    // Мультиплатформенные подпроекты
    "ok-propertysale-mp-common",
    "ok-propertysale-mp-pipelines",
    "ok-propertysale-mp-pipelines-validation",
    "ok-propertysale-mp-transport-ps"
)
