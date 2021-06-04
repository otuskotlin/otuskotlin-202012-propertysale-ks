import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
    kotlin("js") apply false
    kotlin("kapt") apply false
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
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://jitpack.io") }
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

    plugins.withId("org.jetbrains.kotlin.jvm") {
        tasks {
            withType<KotlinCompile> {
                kotlinOptions {
                    freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
                    jvmTarget = javaVersion
                }
            }
        }
    }
}
