pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
    }
}

rootProject.name = "otuskotlin-202012-propertysale-ks"

include(
    "ok-propertysale-be-common",
    "ok-propertysale-be-transport",
    "ok-propertysale-fe-common",
    "ok-propertysale-mp-common",
    "ok-propertysale-mp-mappers-ps",
    "ok-propertysale-mp-transport-ps"
)
