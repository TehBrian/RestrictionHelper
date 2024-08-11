plugins {
  id("restrictionhelper.java-conventions")
}

repositories {
  mavenCentral()
}

dependencies {
  compileOnly("org.slf4j:slf4j-api:2.0.9")
  compileOnly("org.jspecify:jspecify:1.0.0")
}
