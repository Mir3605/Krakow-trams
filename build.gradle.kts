plugins {
    kotlin("jvm") version "2.2.20"
}

group = "mc.krakow"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.sqlite.jdbc)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}