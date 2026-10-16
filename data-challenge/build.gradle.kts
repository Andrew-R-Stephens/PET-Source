plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    // Android Target Configuration (AGP KMP DSL)
    android {
        namespace = "com.tritiumgaming.data.challenge"
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
    val xcfName = "DataDifficulty"

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


                // Keep while transitioning away from monolithic shared module
                api(project(":data-difficultysetting"))
                api(project(":data-difficulty"))
                api(project(":data-map"))
                //api(project(":data-equipment"))

                // Project Dependencies
                api(project(":core-common"))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(project(":data-codex"))
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
                // Add iOS-specific dependencies here if needed
            }
        }
    }
}
