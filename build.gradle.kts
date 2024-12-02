// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.toolsBuild.gradle)
        classpath(libs.googleGms.services)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        // Add the Crashlytics Gradle plugin
        classpath (libs.firebase.crashlyticsGradle)
        // Add the dependency for the Performance Monitoring plugin
        classpath (libs.firebase.perfPlugin)
        classpath (libs.kotlin.gradlePlugin)
        // Performance Monitoring plugin
    }

}

plugins {
    alias(libs.plugins.plugin.android.application).apply(false)
    alias(libs.plugins.plugin.android.library).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.plugin.realmDb.kotlin).apply(false)
    alias(libs.plugins.plugin.kotlin.android).apply(false)

    alias(libs.plugins.plugin.kotlin.compose.compiler).apply(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
