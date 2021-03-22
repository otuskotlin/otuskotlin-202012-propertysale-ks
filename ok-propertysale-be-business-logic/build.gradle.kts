plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion: String by project
    val junitVersion: String by project

    implementation(project(":ok-propertysale-mp-common"))
    implementation(project(":ok-propertysale-be-common"))
    implementation(project(":ok-propertysale-mp-pipelines"))
    implementation(project(":ok-propertysale-mp-pipelines-validation"))

    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test-junit5"))
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
