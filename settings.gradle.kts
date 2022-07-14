rootProject.name = "sample-project"

// The included modules
include("sampleproject-api")
include("sampleproject-core")

pluginManagement{
    // The repositories where we want to load our plugins from
    repositories{
        // default portal
        gradlePluginPortal()
        // The eldonexus which contains the publish data plugin
        maven("https://eldonexus.de/repository/maven-public/")
    }
}
