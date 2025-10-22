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
    ":data-account",
    ":data-codex",
    ":data-contributor",
    ":data-difficulty",
    ":data-evidence",
    ":data-ghost",
    ":data-ghostbox",
    ":data-ghostname",
    ":data-language",
    ":data-map",
    ":data-marketplace",
    ":data-mission",
    ":data-newsletter",
    ":data-palette",
    ":data-preferences",
    ":data-review",
    ":data-typography",
    ":database-unknown",
)
