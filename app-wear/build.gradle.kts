plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
}

android {
    namespace = "com.tritiumgaming.phasmophobiaevidencepicker.wear"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.TritiumGaming.phasmophobiaevidencepicker"
        minSdk = 30
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.jetbrains.kotlinx.coroutines)
    implementation(libs.jetbrains.kotlinx.serialization.json)

    implementation(libs.android.playServices.wearable)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.wear.compose.foundation)
    implementation(libs.androidx.wear.compose.material)
    //implementation(libs.androidx.wear.compose.navigation)
    implementation(libs.androidx.wear.tooling.preview)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation("androidx.compose.ui:ui-geometry:1.12.1")
    implementation("androidx.compose.ui:ui-text:1.12.1")
    implementation("androidx.compose.ui:ui-unit:1.12.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.11.0")


    debugImplementation(libs.androidx.compose.ui.tooling)
    debugRuntimeOnly(libs.androidx.compose.ui.testManifest)


    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    implementation(project(":data-wearable"))
    implementation(project(":data-codex"))
    implementation(project(":data-difficulty"))
    implementation(project(":data-evidence"))
    implementation(project(":data-journal"))
    implementation(project(":data-map"))
    implementation(project(":data-palette"))
    implementation(project(":data-typography"))
}
