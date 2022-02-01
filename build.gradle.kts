plugins {
    id("net.kyori.indra.publishing.sonatype") version "2.1.0"
}

group = "xyz.tehbrian.restrictionhelper"
version = "0.2.0-SNAPSHOT"
description = "A small shade-in dependency for plugin developers, to ease the pain of checking the building restrictions of various plugins."

indraSonatype {
    useAlternateSonatypeOSSHost("s01")
}
