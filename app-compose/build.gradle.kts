//import org.gradle.kotlin.dsl.android

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
    //id 'kotlin-android'

    // Apply the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")

    id("io.realm.kotlin")
    //id("org.jetbrains.kotlin.plugin.compose") version "2.0.2" // this version matches your Kotlin version

    alias(libs.plugins.plugin.kotlin.compose.compiler)
}

android {
    namespace = "com.tritiumgaming.phasmophobiaevidencepicker"

    compileSdk = 35

    buildToolsVersion = "35.0.0"

    /*
     *  Compose Options
     *  ----------------
    */
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    kotlin {
        jvmToolchain(17)
    }
    /* ---------------- */

    bundle {
        language {
            enableSplit = false
        }
    }

    val playCoreDirectory =
        "G:\\Programs\\AndroidStudioRepositories\\play-core-native-sdk-1.10.0\\play-core-native-sdk"

    defaultConfig {

        applicationId = "com.TritiumGaming.phasmophobiaevidencepicker"

        minSdk = 23
        targetSdk = 35
        versionCode = 114
        versionName = "9.0.3"

        @Suppress("UnstableApiUsage")
        externalNativeBuild {
            cmake {
                arguments("-DANDROID_STL=c++_static", "-DPLAYCORE_LOCATION=$playCoreDirectory")
            }
        }
        ndk {
            abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // PLAYCORE
            proguardFile("$playCoreDirectory/proguard/common.pgcfg")
            proguardFile("$playCoreDirectory/proguard/per-feature-proguard-files")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // PLAYCORE
            proguardFile("$playCoreDirectory/proguard/common.pgcfg")
            proguardFile("$playCoreDirectory/proguard/per-feature-proguard-files")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        disable.add("RestrictedApi")
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/java")
            java.srcDir("src/main/kotlin")
            // Potential fix for librealmc
            jniLibs.srcDir("src/main/jniLibs")
        }
    }

    // Potential fix for librealmc
    packaging {
        resources.excludes.add("lib/arm64-v8a/librealm-jni.so")
    }

}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    implementation(libs.firebase.analytics)
    implementation(libs.androidx.core.ktx)

    implementation(libs.android.support.multidex)

    implementation(libs.kotlin.stdlib)
    implementation(libs.google.gson)

    // PRIMARY
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragmentKtx)
    implementation(libs.androidx.navigation.uiKtx)
    implementation(libs.androidx.cardview.core)
    implementation(libs.androidx.legacy.supportV4)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Google Credential Manager ----
    implementation(libs.androidx.credentials.core)
    implementation(libs.androidx.credentials.playServicesAuth)
    implementation(libs.firebaseUi.auth)
    // Phillip Lackner suggested for OTC (one-tap client)
    implementation(libs.firebase.authKtx)
    implementation(libs.android.playServices.auth)
    // ----------------------------------

    // GOOGLE FIREBASE
    // Import the BoM for the Firebase platform
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))
    // GOOGLE FIREBASE FIRESTORE
    implementation(libs.firebase.firestore)
    // GOOGLE BILLING API
    implementation(libs.android.billing.core)


    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.crashlyticsCore)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)
    // GOOGLE ADS
    implementation(libs.android.playServices.ads)
    implementation(libs.android.ump.core)

    //GOOGLE GSON
    implementation(libs.google.gson)

    // IN-APP REVIEWS
    /* This dependency is downloaded from the Google’s Maven repository.
     * So, make sure you also include that repository in your project's build.gradle file.*/
    implementation(libs.android.play.core.review)
    // For Kotlin users also add the Kotlin extensions library for Play Core:
    implementation(libs.android.play.coreKtx.review)

    // IN-APP UPDATES
    /* This dependency is downloaded from the Google’s Maven repository.
     * So, make sure you also include that repository in your project's build.gradle file.*/
    implementation(libs.android.play.core.update)
    // For Kotlin users also add the Kotlin extensions library for Play Core:
    implementation(libs.android.play.coreKtx.update)

    // FRAGMENTS
    // Java language implementation
    // implementation libs.androidx.fragment
    implementation(libs.androidx.fragmentKtx)

    // For use with animated gifs
    implementation(libs.droidsonroids.gifDrawable)

    // DO NOT UPDATE THIS -- BREAKING CHANGES
    //implementation (libs.commonsIo.core)

    /*
        ---- START----
        ANDROIDX LEANBACK
    */
    implementation(libs.androidx.leanback.core)
    // leanback-preference is an add-on that provides a settings UI for TV apps.
    implementation(libs.androidx.leanback.preference)
    // leanback-paging is an add-on that simplifies adding paging support to a RecyclerView Adapter.
    implementation(libs.androidx.leanback.paging)
    // leanback-tab is an add-on that provides customized TabLayout to be used as the top navigation bar.
    implementation(libs.androidx.leanback.tab)

    /*
        ---- START----
        ANDROID COMPOSE
    */
    implementation(platform(libs.androidx.compose.bom))
    //implementation (libs.androidx.compose.animation)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    /* Material Design 3 */
    implementation(libs.androidx.compose.material3)
    // Optional - Add window size utils
    implementation(libs.androidx.compose.material3.windowSizeClass)

    // Compose UI
    implementation(libs.androidx.compose.ui.core)

    implementation(libs.androidx.compose.ui.toolingPreview) // Compose UI Previews
    debugImplementation(libs.androidx.compose.ui.tooling) // Compose UI Previews

    /*Optional - Included automatically by material, only add when you need
    the icons but not the material library (e.g. when using Material3 or a
    custom design system based on Foundation)*/
    //implementation(libs.androidx.compose.material.iconsCore)
    //implementation(libs.androidx.compose.material.iconsExtended) // Optional - Add full set of material icons
    implementation(libs.androidx.compose.runtime.liveData) // Optional - Integration with LiveData
    implementation(libs.androidx.compose.runtime.rxJava2) // Optional - Integration with RxJava

    //implementation(libs.androidx.compose.material.core)

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coilKt.compose)
    implementation(libs.coilKt.network)
    implementation(libs.coilKt.gif)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    /*
        ----- END -----
    */

    /*
     * Realms DB
     */
    implementation(libs.kotlinx.coroutines)
    implementation(libs.realmDb.kotlin.libraryCore)
    implementation(libs.realmDb.kotlin.librarySync)
}