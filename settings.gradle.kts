pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeMovie"
include(":app")
include(":core")
include(":core:domain")
include(":core:network")
include(":core:repository")
include(":feature")
include(":feature:popularmovies")
include(":base")
include(":feature:movie-detail")
include(":core:testing")
