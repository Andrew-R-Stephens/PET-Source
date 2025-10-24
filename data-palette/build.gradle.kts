import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.gms.services)
}

android {
    namespace = "com.tritiumgaming.data.palette"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // GOOGLE BILLING API
    implementation(libs.android.billing.core)

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

    implementation(project(":shared"))
    implementation(project(":core-common"))
    implementation(project(":core-resources"))
}