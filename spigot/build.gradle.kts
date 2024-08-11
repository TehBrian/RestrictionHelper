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

	compileOnly("org.slf4j:slf4j-api:2.0.9")
	compileOnly("org.jspecify:jspecify:1.0.0")

	compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")

	implementation(platform("com.intellectualsites.bom:bom-newest:1.47"))
	compileOnly("com.intellectualsites.plotsquared:plotsquared-core")
	compileOnly("com.intellectualsites.plotsquared:plotsquared-bukkit") { isTransitive = false }

	compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.10")
}
