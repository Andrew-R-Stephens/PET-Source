plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.android.lint)
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    android {
        namespace = "com.tritiumstudios.data.wearable"
        compileSdk {
            version = release(37)
        }
        minSdk = 23

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "data-wearableKit"

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

    sourceSets {
        commonMain {
            dependencies {
                api(libs.jetbrains.kotlinx.coroutines)

                implementation(libs.jetbrains.kotlin.stdlib)
                implementation(libs.jetbrains.kotlinx.serialization.json)

                api(project(":data-map"))
                api(project(":data-difficulty"))
                api(project(":data-journal"))
                api(project(":data-palette"))
                api(project(":data-typography"))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                api(libs.jetbrains.kotlinx.serialization.core)

                // WEARABLE
                implementation(libs.android.playServices.wearable)
                implementation(libs.jetbrains.kotlinx.coroutines.play.services)

                // GOOGLE FIREBASE FIRESTORE
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.auth)
                implementation(libs.firebase.firestore)
                implementation(libs.firebase.crashlytics.core)
                implementation(libs.firebase.analytics)
                implementation(libs.firebase.perfCore)

                api(project(":data-evidence"))
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                runtimeOnly(libs.androidx.core)
                runtimeOnly(libs.androidx.test.runner)

                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }

}