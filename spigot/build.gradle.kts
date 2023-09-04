plugins {
  id("restrictionhelper.java-conventions")
}

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://maven.enginehub.org/repo/")
}

dependencies {
  api(project(":restrictionhelper-core"))
  compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")

  implementation(platform("com.intellectualsites.bom:bom-newest:1.37"))
  compileOnly("com.intellectualsites.plotsquared:plotsquared-core:7.0.0")
  compileOnly("com.intellectualsites.plotsquared:plotsquared-bukkit:7.0.0") { isTransitive = false }
  compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9")
}
