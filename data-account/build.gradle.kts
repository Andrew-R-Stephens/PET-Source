plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    // Android Target Configuration (AGP KMP DSL)
    android {
        namespace = "com.tritiumgaming.data.account"
        compileSdk = 37
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    // iOS Targets
    val xcfName = "DataAccount"

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    // Source Sets
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.jetbrains.kotlin.stdlib)
                implementation(libs.jetbrains.kotlinx.coroutines)
                implementation(libs.jetbrains.kotlinx.serialization.json)

                // Keep while transitioning away from monolithic shared module
                implementation(project(":shared"))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                // Android Core & UI
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.appcompat.core)
                implementation(libs.android.material)

                // Google Firebase Platform (Android-only until Firebase KMP is added)
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.auth)
                implementation(libs.firebase.firestore)
                implementation(libs.firebase.functions)

                // Google Credential Manager
                implementation(libs.android.playServices.auth)
                implementation(libs.androidx.credentials.core)
                implementation(libs.androidx.credentials.playServicesAuth)
                implementation(libs.googleid)

                // Shared project dependencies
                implementation(project(":core-resources"))
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here when needed
            }
        }
    }
}


/*
import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
    // alias(libs.plugins.jetbrains.kotlin.android)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.data.account"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

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
    // GOOGLE FIREBASE FUNCTIONS
    implementation(libs.firebase.functions)

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
}*/
