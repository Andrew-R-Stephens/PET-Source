import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.gms.services)

    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.tritiumgaming.feature.mainmenu"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

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

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

}

dependencies {

    // Import the Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

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

    implementation(project(":core-ui"))
    implementation(project(":core-resources"))
    implementation(project(":shared"))
}