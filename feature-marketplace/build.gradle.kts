import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.gms.services)

    // alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.marketplace"
    compileSdk = 37

    /*
     *  Compose Options
     *  ----------------
    */
    buildFeatures {
        compose = true
    }

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
    lint {
        targetSdk = 37
    }
    testOptions {
        targetSdk = 37
    }

}

dependencies {

    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.runtime)
    api(libs.jetbrains.kotlinx.coroutines)
    api(libs.androidx.compose.ui.core)
    api(libs.androidx.appcompat.core)

    // GOOGLE FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.runtime.annotation)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.window.core)
    implementation(libs.androidx.navigation.compose)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)
    androidTestRuntimeOnly(libs.androidx.test.core)
    androidTestRuntimeOnly(libs.androidx.test.runner)
    androidTestRuntimeOnly(libs.jetbrains.kotlinx.coroutines.test)
    debugImplementation(libs.androidx.compose.ui.testManifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.junit)

    api(project(":core-common"))
    api(project(":core-ui"))
    implementation(project(":core-resources"))

    api(project(":data-ads"))
    api(project(":data-account"))
    api(project(":data-marketplace"))
    api(project(":data-palette"))
    api(project(":data-preferences"))
    implementation(project(":data-typography"))

    

}