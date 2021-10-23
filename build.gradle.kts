plugins {
    id("net.kyori.indra.publishing.sonatype") version "2.0.1"
}

group = "xyz.tehbrian.restrictionhelper"
version = "0.2.0"
description = "A small shade-in dependency for plugin developers, to ease the pain of checking the building restrictions of various plugins."

indraSonatype {
    useAlternateSonatypeOSSHost("s01")
}
