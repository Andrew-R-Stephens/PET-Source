import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.gms.services)

    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.tritiumgaming.feature.language"
    compileSdk = 36

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "36.1.0"

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

}

dependencies {

    implementation(libs.androidx.navigation.compose)

    // Import the Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

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

    /*
        ---- START----
        ANDROID COMPOSE
    */

    // Testing --
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    // ---

    implementation(libs.androidx.compose.foundation)
    /* Material Design 3 */
    implementation(libs.androidx.compose.material3)
    // Optional - Add window size utils
    implementation(libs.androidx.compose.material3.adaptive)

    // Compose UI
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.ui.toolingPreview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.ui.graphics)

    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    implementation(project(":shared"))

}