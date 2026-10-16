import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.gms.services)

    // alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.missions"
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

    api(libs.androidx.compose.runtime)
    api(libs.jetbrains.kotlinx.coroutines)
    api(libs.androidx.appcompat.core)
    api(libs.androidx.compose.ui.core)

    // Import the Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.window.core)


    // Coil
    implementation(libs.coilKt.network)
    runtimeOnly(libs.coilKt.gif)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.junit)

    androidTestRuntimeOnly(libs.androidx.test.runner)
    androidTestRuntimeOnly(libs.jetbrains.kotlinx.coroutines.test)

    testImplementation(libs.junit)

    debugImplementation(libs.androidx.compose.ui.tooling)

    debugRuntimeOnly(libs.androidx.compose.ui.testManifest)

    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    api(project(":data-ghostname"))
    api(project(":data-mission"))
    api(project(":data-difficulty"))
    api(project(":data-operation"))
    implementation(project(":data-challenge"))
    implementation(project(":data-difficultysetting"))
    implementation(project(":data-palette"))
    implementation(project(":data-typography"))

}