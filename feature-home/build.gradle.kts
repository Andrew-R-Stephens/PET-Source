import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.gms.services)

    // alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.home"
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
    api(libs.androidx.compose.ui.core)
    api(libs.androidx.datastore.core)
    api(libs.androidx.datastore.preferences.core)

    // GOOGLE FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation.layout)

    implementation(libs.jetbrains.kotlinx.coroutines)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.testJunit4)

    androidTestRuntimeOnly(libs.androidx.test.runner)
    androidTestRuntimeOnly(libs.jetbrains.kotlinx.coroutines.test)

    testImplementation(libs.junit)

    debugImplementation(libs.androidx.compose.ui.tooling)

    debugRuntimeOnly(libs.androidx.compose.ui.testManifest)

    api(project(":core-common"))
    implementation(project(":core-ui"))
    //implementation(project(":core-resources"))

    api(project(":data-account"))
    api(project(":data-language"))
    api(project(":data-marketplace"))
    api(project(":data-preferences"))
    implementation(project(":data-contributor"))
    implementation(project(":data-newsletter"))
}