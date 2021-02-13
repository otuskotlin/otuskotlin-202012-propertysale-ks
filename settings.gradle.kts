rootProject.name = "otuskotlin-202012-propertysale-ks"

include(
    "ok-propertysale-be-common",
    "ok-propertysale-mp-common"
)

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}
