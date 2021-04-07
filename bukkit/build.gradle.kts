plugins {
    id("java")
    id("maven")
    id("org.checkerframework") version "0.5.19"
}

group = "xyz.tehbrian.restrictionhelper"
version = "0.2.0"

repositories {
    mavenCentral()
    mavenLocal() // for testing

    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    // Restrictions
    maven { url = uri("https://maven.enginehub.org/repo/") } // WorldEdit and WorldGuard
    maven { url = uri("https://mvn.intellectualsites.com/content/groups/public/") } // PlotSquared
}

dependencies {
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")

    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")

    implementation(project(":core"))

    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    // Restrictions
    compileOnly("com.plotsquared:PlotSquared-Core:5.13.3") // PlotSquared Core API
    compileOnly("com.plotsquared:PlotSquared-Bukkit:5.13.3") // PlotSquared Bukkit API
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4") // WorldGuard API
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
