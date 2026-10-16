plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    // ------------------------------------------------------------------------
    // Target Declarations
    // ------------------------------------------------------------------------

    android {
        namespace = "com.tritiumgaming.data.codex"
        compileSdk = 37
        minSdk = 23

        withHostTestBuilder { }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // Standardized iOS target declarations with framework configuration
    val xcfName = "dataCodexKit"
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    // ------------------------------------------------------------------------
    // Compiler Options
    // ------------------------------------------------------------------------

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    // ------------------------------------------------------------------------
    // Source Sets & Dependencies
    // ------------------------------------------------------------------------

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.kotlin.stdlib)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.annotation)

            api(project(":core-resources"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                runtimeOnly(libs.androidx.test.runner)

                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain.dependencies {
            // Add iOS-specific dependencies here
        }
    }
}

