import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.gms.services)

    // alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.customdifficulty"
    compileSdk = 37

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }


    buildTypes {
        debug {
            initWith(getByName("debug"))
        }
        release {
            initWith(getByName("release"))
        }
        create("releaseTest") {
            initWith(getByName("releaseTest"))
        }
    }


    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "36.1.0"

}

dependencies {

    api(libs.jetbrains.kotlinx.coroutines)
    api(libs.androidx.appcompat.core)
    api(libs.androidx.compose.ui.core)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.runtime)

    // GOOGLE FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)

    // Import the Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.window.core)
    implementation(libs.androidx.navigation.compose)


    // Testing --
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    androidTestImplementation(libs.junit)

    androidTestRuntimeOnly(libs.androidx.test.runner)
    androidTestRuntimeOnly(libs.jetbrains.kotlinx.coroutines.test)

    testImplementation(libs.junit)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    // ---

    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    api(project(":data-customdifficulty"))
    implementation(project(":data-palette"))
    implementation(project(":data-typography"))
    implementation(project(":data-difficultysetting"))

}