plugins {
    `java-library`
    id("org.checkerframework")
    id("net.kyori.indra")
    id("net.kyori.indra.checkstyle")
    id("net.kyori.indra.publishing")
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
}

indra {
    javaVersions {
        target(16)
    }

    mitLicense()

    publishReleasesTo("thbn", "https://repo.thbn.me/releases")
    publishSnapshotsTo("thbn", "https://repo.thbn.me/snapshots")

    configurePublications{
        pom {
            url.set("https://github.com/TehBrian/RestrictionHelper")

            developers {
                developer {
                    name.set("TehBrian")
                    url.set("https://tehbrian.xyz")
                    email.set("tehbrian@protonmail.com")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/TehBrian/RestrictionHelper.git")
                developerConnection.set("scm:git:ssh://github.com/TehBrian/RestrictionHelper.git")
                url.set("https://github.com/TehBrian/RestrictionHelper.git")
            }
        }
    }
}
