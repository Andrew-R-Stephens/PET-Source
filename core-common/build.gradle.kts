plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {

    // Target declarations
    android {
        namespace = "com.tritiumgaming.core.common"
        compileSdk = 37
        minSdk = 23

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
    val xcfName = "coreCommonKit"

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

    // Source set declarations
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.jetbrains.kotlin.stdlib)
                api(libs.jetbrains.kotlinx.coroutines)
                implementation(libs.androidx.navigation3.runtime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                /* APIS */

                api(libs.jetbrains.kotlinx.serialization.core)
                // In-App Updates
                api(libs.android.play.core.update)
                api(libs.android.ump.core)
                api(libs.androidx.window.core)
                api(libs.androidx.navigation3.runtime)
                api(libs.androidx.credentials.core)
                api(libs.androidx.activity)
                api(libs.androidx.compose.runtime)


                /* IMPLEMENTATIONS */

                // Android UI & Core
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.core)
                implementation(libs.androidx.annotation)
                implementation(libs.androidx.appcompat.resources)
                implementation(libs.androidx.compose.runtime.saveable)

                // Google Ads & Play Services
                implementation(libs.android.playServices.ads)

                // Firebase
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.analytics)

            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                runtimeOnly(libs.androidx.test.runner)
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain {
            dependencies {
                // iOS-specific dependencies go here
            }
        }
    }
}
