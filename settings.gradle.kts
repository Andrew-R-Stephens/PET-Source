pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "PhasmophobiaEvidencePicker"
include(
    ":app-compose",
    ":app-legacy",
    ":shared",
    ":core-resources",
    ":core-ui"
)
include(":feature-operation")
include(":core-common")
include(":data-evidence")
include(":data-ghost")
include(":data-difficulty")
include(":data-language")
include(":data-codex")
include(":data-map")
include(":data-contributor")
include(":data-account")
include(":data-mission")
include(":data-newsletter")
include(":data-ghostbox")
include(":data-ghostname")
include(":data-globalpreferences")
include(":database-unknown")
include(":data-review")
include(":data-marketplace")
include(":feature-home")
include(":core-di")
