plugins {
  id("restrictionhelper.java-conventions")
}

repositories {
  mavenCentral()
}

dependencies {
  api("org.slf4j:slf4j-api:2.0.9")
  implementation("org.jspecify:jspecify:1.0.0")
}
