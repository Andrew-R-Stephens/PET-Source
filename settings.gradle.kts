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
    ":app-legacy"
)

include(
    ":shared"
)

include(
    ":core-common",
    ":core-resources",
    ":core-ui"
)

include(
    ":feature-operation",
    ":feature-home",
    ":feature-core"
)

include(
    ":data-evidence",
    ":data-ghost",
    ":data-difficulty",
    ":data-language",
    ":data-codex",
    ":data-map",
    ":data-contributor",
    ":data-account",
    ":data-mission",
    ":data-newsletter",
    ":data-ghostbox",
    ":data-ghostname",
    ":data-preferences",
    ":database-unknown",
    ":data-review",
    ":data-marketplace"
)
