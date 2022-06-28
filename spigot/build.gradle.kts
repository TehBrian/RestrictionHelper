plugins {
    id("restrictionhelper.java-conventions")
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }

    // Built-in Restrictions
    maven("https://maven.enginehub.org/repo/") // WorldGuard
}

dependencies {
    api(project(":restrictionhelper-core"))

    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")

    // Built-in Restrictions
    compileOnly("com.plotsquared:PlotSquared-Core:6.9.1") { isTransitive = false }
    compileOnly("com.plotsquared:PlotSquared-Bukkit:6.9.1") { isTransitive = false }
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7")
}
