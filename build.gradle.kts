plugins {
    kotlin("jvm") version "2.4.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("io.github.gulliby.windowgate.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
