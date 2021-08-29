plugins {
    id("restrictionhelper.java-conventions")
}

repositories {
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    // Restrictions
    maven { url = uri("https://maven.enginehub.org/repo/") } // WorldEdit and WorldGuard
    maven { url = uri("https://mvn.intellectualsites.com/content/groups/public/") } // PlotSquared
}

dependencies {
    api(project(":restrictionhelper-core"))

    implementation("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")

    // Restrictions
    compileOnly("com.plotsquared:PlotSquared-Core:5.13.11") // PlotSquared Core API
    compileOnly("com.plotsquared:PlotSquared-Bukkit:5.13.11") // PlotSquared Bukkit API
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.4") // WorldGuard API
}
