plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    // ------------------------------------------------------------------------
    // Target Declarations
    // ------------------------------------------------------------------------

    android {
        namespace = "com.tritiumgaming.data.operation"
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
    val xcfName = "dataOperationKit"
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
            api(libs.jetbrains.kotlinx.coroutines)

            // Internal Module Dependencies
            api(project(":data-challenge"))
            api(project(":data-customdifficulty"))
            api(project(":data-evidence"))
            api(project(":data-ghost"))
            api(project(":data-journal"))
            api(project(":data-mission"))
            api(project(":data-trait"))
            api(project(":data-codex"))
            api(project(":data-difficulty"))
            api(project(":data-difficultysetting"))
            api(project(":data-ghostname"))
            api(project(":data-map"))
            api(project(":data-phase"))
            api(project(":data-sanity"))
            api(project(":data-temperature"))

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.compose.runtime.annotation)
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
