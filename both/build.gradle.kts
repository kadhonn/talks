plugins {
    id("java")
}

group = "com.dynatrace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":subsystem"))
}
