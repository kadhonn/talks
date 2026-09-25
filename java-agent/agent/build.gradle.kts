plugins {
    id("java")
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Premain-Class" to "at.abl.agenttalk.agent.Agent",
            "Can-Retransform-Classes" to true,
            "Can-Redefine-Classes" to true,
        )
    }
}