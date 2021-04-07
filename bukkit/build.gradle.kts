plugins {
    id("restrictionhelper.java-conventions")
}

repositories {
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
    api(project(":core"))

    implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    // Restrictions
    implementation("com.plotsquared:PlotSquared-Core:5.13.3") // PlotSquared Core API
    implementation("com.plotsquared:PlotSquared-Bukkit:5.13.3") // PlotSquared Bukkit API
    implementation("com.sk89q.worldguard:worldguard-bukkit:7.0.4") // WorldGuard API
}
