plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    api("org.checkerframework:checkerframework-gradle-plugin:0.6.5")
    api("net.kyori:indra-common:2.0.6")
}
