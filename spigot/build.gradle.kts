plugins {
    id("restrictionhelper.java-conventions")
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/") { // MiniMessage for PlotSquared
        name = "papermc-repo"
    }
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }

    // Restrictions
    maven("https://maven.enginehub.org/repo/") // WorldEdit and WorldGuard
    maven("https://mvn.intellectualsites.com/content/groups/public/") // PlotSquared
}

dependencies {
    api(project(":restrictionhelper-core"))

    compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")

    // Restrictions
    compileOnly("com.plotsquared:PlotSquared-Core:6.3.0") // PlotSquared Core API
    compileOnly("com.plotsquared:PlotSquared-Bukkit:6.3.0") // PlotSquared Bukkit API
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.6") // WorldGuard API
}
