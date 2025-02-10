plugins {
    id("java")
    id("io.freefair.lombok") version "8.12.1"
}

group = "todo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.15.2")


}

tasks.test {
    useJUnitPlatform()
}