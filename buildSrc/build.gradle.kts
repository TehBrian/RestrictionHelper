plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    api("org.checkerframework:checkerframework-gradle-plugin:0.6.10")
    api("net.kyori:indra-common:2.1.1")
}
