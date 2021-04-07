plugins {
    id("java")
    id("maven")
    id("org.checkerframework")
}

group = "xyz.tehbrian.restrictionhelper"
version = "0.2.0"

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")

    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()
    }
}
