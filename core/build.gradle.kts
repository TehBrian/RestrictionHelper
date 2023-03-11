plugins {
  id("restrictionhelper.java-conventions")
}

repositories {
  mavenCentral()
}

dependencies {
  api("org.slf4j:slf4j-api:2.0.6")
  compileOnly("org.checkerframework:checker-qual:3.32.0")
}
