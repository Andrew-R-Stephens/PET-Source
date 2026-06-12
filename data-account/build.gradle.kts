import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
    // alias(libs.plugins.jetbrains.kotlin.android)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.data.account"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // GOOGLE FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // GOOGLE FIREBASE AUTH
    implementation(libs.firebase.auth)
    // GOOGLE FIREBASE FIRESTORE
    implementation(libs.firebase.firestore)

    // --- Google Credential Manager ----
    //noinspection LoginCredentials
    implementation(libs.android.playServices.auth)
    //noinspection LoginCredentials
    implementation(libs.androidx.credentials.core)
    //noinspection LoginCredentials
    implementation(libs.androidx.credentials.playServicesAuth)
    //noinspection LoginCredentials
    implementation(libs.googleid)
    // ----------------------------------

    implementation(project(":shared"))
    implementation(project(":core-resources"))
}