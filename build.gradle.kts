import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("js") apply false
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    idea
}

group = "ru.otus.otuskotlin.propertysale"
description = "OTUS Kotlin course project"
version = "0.0.1"

allprojects {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.gradle.idea")

    idea {
        module {
            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    val javaVersion: String by project

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = javaVersion
            }
        }
    }
}
