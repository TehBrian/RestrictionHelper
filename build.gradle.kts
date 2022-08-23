plugins {
  id("net.kyori.indra.publishing.sonatype") version "2.1.1"
}

group = "xyz.tehbrian.restrictionhelper"
version = "0.3.1"
description = "A small library that eases the pain of checking the build restrictions of various plugins."

indraSonatype {
  useAlternateSonatypeOSSHost("s01")
}
