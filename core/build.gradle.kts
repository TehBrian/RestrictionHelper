plugins {
  id("restrictionhelper.java-conventions")
}

repositories {
  mavenCentral()
}

dependencies {
  api("org.slf4j:slf4j-api:2.0.0")
  compileOnly("org.checkerframework:checker-qual:3.24.0")
}
