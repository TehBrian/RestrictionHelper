plugins {
    `java-library`
    id("org.checkerframework")
    id("net.kyori.indra")
    id("net.kyori.indra.checkstyle")
    id("net.kyori.indra.publishing")
}

group = "xyz.tehbrian.restrictionhelper"
version = "0.2.0"
description = "A small shade-in dependency for plugin developers, to ease the pain of checking the building restrictions of various plugins."

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
}

indra {
    javaVersions {
        target(11)
        testWith(11, 16)
    }

    mitLicense()

    configurePublications{
        pom {
            url.set("https://github.com/ItsTehBrian/RestrictionHelper")

            developers {
                developer {
                    id.set("TehBrian")
                    name.set("Brian Packard")
                    url.set("https://tehbrian.xyz")
                    email.set("tehbrian(at)outlook(dot)com")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/ItsTehBrian/RestrictionHelper.git")
                developerConnection.set("scm:git:ssh://github.com/ItsTehBrian/RestrictionHelper.git")
                url.set("https://github.com/ItsTehBrian/RestrictionHelper.git")
            }
        }
    }
}
