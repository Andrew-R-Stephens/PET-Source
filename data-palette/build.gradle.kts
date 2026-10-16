plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.gms.services)
}

kotlin {
    // ------------------------------------------------------------------------
    // Target Declarations
    // ------------------------------------------------------------------------

    android {
        namespace = "com.tritiumgaming.data.palette"
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
    val xcfName = "dataPaletteKit"
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
            // Kotlin Standard Library
            implementation(libs.jetbrains.kotlin.stdlib)

            // Internal Module Dependencies
            api(project(":core-common"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Android Firebase Native Dependencies (using BoM)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.functions)
            implementation(libs.firebase.crashlytics.core)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.perfCore)
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
