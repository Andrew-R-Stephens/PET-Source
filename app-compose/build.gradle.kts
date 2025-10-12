import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)

    alias(libs.plugins.gms.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)

    alias(libs.plugins.devtools.ksp)
}

android {

    namespace = "com.tritiumgaming.core"
    compileSdk = 36

    /*
     *  Compose Options
     *  ----------------
    */
    buildFeatures {
        compose = true
    }

    /*composeOptions{
        kotlinCompilerExtensionVersion = "1.5.15"
    }*/

    /* ---------------- */

    bundle {
        language {
            enableSplit = false
        }
    }

    defaultConfig {

        applicationId = "com.TritiumGaming.phasmophobiaevidencepicker"

        minSdk = 23
        targetSdk = 36
        versionCode = 134
        versionName = "10.0.1-compose.a.2"

        ndk {
            abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildToolsVersion = "36.0.0"

}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles.addAll(
        rootProject.layout.projectDirectory.file("stability_config.conf")
    )
}

dependencies {

    // PRIMARY
    implementation(libs.android.support.multidex)

    implementation(libs.jetbrains.kotlin.stdlib)
    implementation(libs.jetbrains.kotlinx.coroutines)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragmentKtx)
    implementation(libs.androidx.navigation.uiKtx)
    implementation(libs.androidx.cardview.core)
    implementation(libs.androidx.legacy.supportV4)
    implementation(libs.android.material)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.kotlin)

    // --- Google Credential Manager ----
    implementation(libs.android.playServices.auth)
    implementation(libs.androidx.credentials.core)
    implementation(libs.androidx.credentials.playServicesAuth)
    implementation(libs.googleid)
    // ----------------------------------

    // GOOGLE FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // GOOGLE FIREBASE AUTH
    implementation(libs.firebase.auth)
    // GOOGLE FIREBASE FIRESTORE
    implementation(libs.firebase.firestore)
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    // GOOGLE FIREBASE ANALYTICS
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)

    // GOOGLE BILLING API
    implementation(libs.android.billing.core)

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

    // Testing --
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    // ---

    implementation(libs.androidx.compose.foundation)
    /* Material Design 3 */
    implementation(libs.androidx.compose.material3)
    // Optional - Add window size utils
    //implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.material3.adaptive)

    // Compose UI
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.ui.toolingPreview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    /*Optional - Included automatically by material, only add when you need
    the icons but not the material library (e.g. when using Material3 or a
    custom design system based on Foundation)*/
    implementation(libs.androidx.compose.runtime.liveData) // Optional - Integration with LiveData
    implementation(libs.androidx.compose.runtime.rxJava2) // Optional - Integration with RxJava

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    // Coil
    implementation(libs.coilKt.compose)
    implementation(libs.coilKt.network)
    implementation(libs.coilKt.gif)

    implementation(libs.androidx.ui.graphics)

    /*
        ----- END OF COMPOSE -----
    */

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    /*
     * Ktor
     */
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.serialization.kotlinx.cbor)
    implementation(libs.ktor.serialization.kotlinx.protobuf)
    implementation(libs.ktor.client.logging)

    /*
     * Realms DB
     */
    implementation(libs.realm.kotlin.library.base)
    implementation(libs.realm.kotlin.library.sync)

    /*
     * Room DB
     */
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava2)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.guava)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.paging)

    /*
     * Include Shared Module for KMP
     */
    implementation(project(":shared"))

    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    implementation(project(":database-unknown"))

    implementation(project(":feature-core"))
    implementation(project(":feature-home"))
    implementation(project(":feature-operation"))


}