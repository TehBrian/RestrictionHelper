plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    api("org.checkerframework:checkerframework-gradle-plugin:0.5.19")
    api("net.kyori:indra-common:2.0.6")
}
