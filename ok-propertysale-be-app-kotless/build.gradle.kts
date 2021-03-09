import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm")
    id("io.kotless")
    kotlin("plugin.serialization")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val kotlessVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":ok-propertysale-be-common"))
    api(project(":ok-propertysale-mp-transport-ps"))
    api(project(":ok-propertysale-be-mappers-ps"))

    implementation(kotlin("stdlib"))
    implementation("io.kotless", "kotless-lang", kotlessVersion)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

kotless {
    config {
        bucket = "ru.skubatko.propertysale"
        dsl {
            type = io.kotless.DSLType.Kotless
        }
        terraform {
            profile = "default"
            region = "eu-north-1"
        }
        optimization {
            //default config
            autowarm = io.kotless.plugin.gradle.dsl.KotlessConfig.Optimization.Autowarm(enable = false, minutes = -1)
        }
    }
    webapp {
        route53 = io.kotless.plugin.gradle.dsl.Webapp.Route53("propertysale", "skubatko.ru","skubatko.ru")
        lambda {
            memoryMb = 256
        }
    }

    extensions {
        local {
            //enable AWS emulation (disabled by default)
            useAWSEmulation = true
        }
        terraform {
            allowDestroy = true
        }
    }
}
