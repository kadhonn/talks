plugins {
    id("java")
    id("application")
}

group = "at.abl.agenttalk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "at.abl.Main"
    applicationDefaultJvmArgs = listOf("-javaagent:agent/build/libs/agent.jar")
}