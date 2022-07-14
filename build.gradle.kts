import de.chojo.PublishData
import org.cadixdev.gradle.licenser.Licenser

plugins {
    java
    `java-library`
    `maven-publish`
    // license plugin to add license headers to files
    id("org.cadixdev.licenser") version "0.6.1"
    // plugin used for publishing to a maven repository
    id("de.chojo.publishdata") version "1.0.8"
    // shadow plugin to shade required dependencies
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.chojo"
version = "1.0.0"


subprojects {
    apply {
        // We want to apply several plugins to subprojects
        plugin<JavaPlugin>()
        plugin<Licenser>()
        plugin<PublishData>()
        plugin<JavaLibraryPlugin>()
        plugin<MavenPublishPlugin>()
    }
}

// we configure some plugins for all moduls
allprojects {
    // Our most commonly used repositories
    repositories {
        mavenCentral()
        // This repo contains, stable, dev and snapshots
        maven("https://eldonexus.de/repository/maven-public/")
        // This repo contains several common repositories.
        maven("https://eldonexus.de/repository/maven-proxies/")
    }

    java {
        // We want to generate sources
        withSourcesJar()
        // We want to generate javadocs
        withJavadocJar()
    }

    // some base dependencies we wanna have everywhere
    dependencies {
        testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.8.1")
        testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.8.1")
    }

    license {
        // We configure the plugin to use the content of a file called HEADER.txt in our project root
        header(rootProject.file("HEADER.txt"))
        // The header should be applied to all files in any directory which end with .java
        include("**/*.java")
    }

    publishData {
        useEldoNexusRepos(true)
    }

    publishing {
        publications.create<MavenPublication>("maven") {
            publishData.configurePublication(this)
            pom {
                url.set("https://github.com/rainbowdashlabs/sample-project")
                developers {
                    developer {
                        name.set("Florian Fülling")
                        url.set("https://github.com/rainbowdashlabs")
                    }
                }
                licenses {
                    license {
                        name.set("GNU Affero General Public License v3.0")
                        url.set("https://github.com/rainbowdashlabs/sample-project/blob/master/LICENSE.md")
                    }
                }
            }
        }

        repositories {
            maven {
                authentication {
                    credentials(PasswordCredentials::class) {
                        username = System.getenv("NEXUS_USERNAME")
                        password = System.getenv("NEXUS_PASSWORD")
                    }
                }

                setUrl(publishData.getRepository())
                name = "EldoNexus"
            }
        }
    }

    // We configure some general tasks for our modules
    tasks {
        test {
            dependsOn(licenseCheck)
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }
}
