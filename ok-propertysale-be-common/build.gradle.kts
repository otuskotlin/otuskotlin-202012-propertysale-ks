plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {

    val slf4jVersion: String by project
    val logbackEncoderVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
