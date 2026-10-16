import com.android.build.api.dsl.ApplicationExtension

plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)

    alias(libs.plugins.gms.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)

    alias(libs.plugins.devtools.ksp)
}

configure<ApplicationExtension> {

    namespace = "com.tritiumgaming.core"
    compileSdk = 37

    /*
     *  Compose Options
     *  ----------------
    */
    buildFeatures {
        compose = true
        buildConfig = true
    }

    /* ---------------- */

    bundle {
        language {
            enableSplit = false
        }
    }

    defaultConfig {

        applicationId = "com.TritiumGaming.phasmophobiaevidencepicker"

        minSdk = 24
        targetSdk = 37
        versionCode = 205
        versionName = "1.0.3"

        ndk {
            abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        buildConfigField("boolean", "USE_NEWSLETTER", "true")
        buildConfigField("boolean", "USE_FIRESTORE", "true")
        buildConfigField("boolean", "USE_ACCOUNT", "true")
        buildConfigField("boolean", "USE_MARKETPLACE", "true")
    }

    /*flavorDimensions += "minSdk"

    productFlavors {
        create("legacy") {
            dimension = "minSdk"
            minSdk = 23
            versionCode = 206
        }
        create("standard") {
            dimension = "minSdk"
            minSdk = 24
            versionCode = 207
        }
    }*/

    buildTypes {
        debug {
            isMinifyEnabled = false
            //noinspection NotShrinkingResources
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
        create("releaseTest") {
            initWith(getByName("release"))
            buildConfigField("boolean", "USE_NEWSLETTER", "false")
            buildConfigField("boolean", "USE_FIRESTORE", "false")
            buildConfigField("boolean", "USE_ACCOUNT", "false")
            buildConfigField("boolean", "USE_MARKETPLACE", "false")
        }
    }

    buildToolsVersion = "36.1.0"

}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles.addAll(
        rootProject.layout.projectDirectory.file("stability_config.conf")
    )
}

dependencies {

    // PRIMARY
    implementation(libs.jetbrains.kotlin.stdlib)
    implementation(libs.jetbrains.kotlinx.coroutines)

    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.appcompat.core)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)

    // --- Google Credential Manager ----
    //noinspection LoginCredentials
    implementation(libs.android.playServices.auth)
    //noinspection LoginCredentials
    implementation(libs.androidx.credentials.playServicesAuth)
    // ----------------------------------

    // GOOGLE FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // GOOGLE FIREBASE AUTH
    implementation(libs.firebase.auth)
    // GOOGLE FIREBASE FIRESTORE
    implementation(libs.firebase.firestore)
    // GOOGLE FIREBASE FUNCTIONS
    implementation(libs.firebase.functions)
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    // GOOGLE FIREBASE ANALYTICS
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)

    // GOOGLE BILLING API
    implementation(libs.android.billing.core)

    // GOOGLE ADS
    implementation(libs.android.ump.core)

    // IN-APP UPDATES
    implementation(libs.android.play.core.update)
    // For use with animated gifs
    implementation(libs.droidsonroids.gifDrawable)


    /*
        ---- START----
        ANDROID COMPOSE
    */
    implementation(platform(libs.androidx.compose.bom))

    // Testing --
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.testManifest)
    // ---

    implementation(libs.androidx.compose.foundation)
    /* Material Design 3 */
    implementation(libs.androidx.compose.material3)
    // Compose UI
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.ui.toolingPreview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    // Coil
    implementation(libs.coilKt.network)
    implementation(libs.coilKt.gif)

    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.android.playServices.wearable)
    implementation(libs.jetbrains.kotlinx.coroutines.play.services)

    /*
        ----- END OF COMPOSE -----
    */

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    /*
     * Ktor
     */
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.cio)

    /*
     * Realms DB
     */
    implementation(libs.realm.kotlin.library.sync)

    /*
     * Room DB
     */
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.ktx)

    /*
     * Include Shared Module for KMP
     */
    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    implementation(project(":database-local"))

    implementation(project(":data-challenge"))
    implementation(project(":data-customdifficulty"))
    implementation(project(":data-language"))
    implementation(project(":data-review"))

    implementation(project(":feature-core"))

    implementation(project(":feature-start"))
    implementation(project(":feature-account"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-language"))
    implementation(project(":feature-home"))
    implementation(project(":feature-about"))
    implementation(project(":feature-newsletter"))
    implementation(project(":feature-marketplace"))
    implementation(project(":feature-codex"))
    implementation(project(":feature-investigation"))
    implementation(project(":feature-customdifficulty"))
    implementation(project(":feature-mapviewer"))
    implementation(project(":feature-objectiveboard"))
    implementation(project(":feature-operation"))

}
