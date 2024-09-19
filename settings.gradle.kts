pluginManagement {
    includeBuild("build-logic")

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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Tidify"
include(":app")


include(
    ":core:data",
    ":core:data-api",
    ":core:datastore",
    ":core:room",
    ":core:datastore-proto",
    ":core:designsystem",
    ":core:model",
    ":core:domain"
)

include(
    ":feature:main",
    ":feature:working",
    ":feature:test"
)
