//project(":PET-Source").projectDir = File(rootDir, "app/")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "PhasmophobiaEvidencePicker"
include(":app")
include(":app-compose")
