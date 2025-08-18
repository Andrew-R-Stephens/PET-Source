import org.jetbrains.kotlin.commonizer.OptimisticNumberCommonizationEnabledKey.alias

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        //classpath(libs.android.toolsBuild.gradle)
        classpath(libs.google.gms.services)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        // Add the Crashlytics Gradle plugin
        classpath (libs.firebase.crashlytics.gradle)
        // Add the dependency for the Performance Monitoring plugin
        classpath (libs.firebase.perfPlugin)
        //classpath (libs.jetbrains.kotlin.gradlePlugin)
        // Performance Monitoring plugin
    }

}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose.compiler) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false

    alias(libs.plugins.io.realm.kotlin) apply false

    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.library) apply false


}


