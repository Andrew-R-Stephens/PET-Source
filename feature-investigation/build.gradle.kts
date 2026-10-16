import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.gms.services)

    // alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.investigation"
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

    // Import the Compose BOM
    api(libs.androidx.appcompat.core)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.core)
    api(libs.jetbrains.kotlinx.coroutines)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.core)

    // Coil
    implementation(libs.coilKt.compose)
    implementation(libs.coilKt.network)
    runtimeOnly(libs.coilKt.gif)

    // Charts
    // implementation(libs.ehsannarmani.compose.charts)

    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    androidTestImplementation(libs.junit)

    debugRuntimeOnly(libs.androidx.compose.ui.testManifest)
    androidTestRuntimeOnly(libs.androidx.test.runner)
    androidTestRuntimeOnly(libs.jetbrains.kotlinx.coroutines.test)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)

    api(project(":core-ui"))
    implementation(project(":core-common"))
    implementation(project(":core-resources"))

    api(project(":data-challenge"))
    api(project(":data-customdifficulty"))
    api(project(":data-difficulty"))
    api(project(":data-evidence"))
    api(project(":data-ghost"))
    api(project(":data-ghostname"))
    api(project(":data-journal"))
    api(project(":data-map"))
    api(project(":data-preferences"))
    api(project(":data-trait"))
    api(project(":data-operation"))
    api(project(":data-difficultysetting"))
    implementation(project(":data-codex"))
    implementation(project(":data-mission"))
    implementation(project(":data-palette"))
    implementation(project(":data-phase"))
    implementation(project(":data-sanity"))
    implementation(project(":data-temperature"))
    implementation(project(":data-typography"))

}