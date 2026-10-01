plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    // ------------------------------------------------------------------------
    // Target Declarations
    // ------------------------------------------------------------------------

    android {
        namespace = "com.tritiumgaming.data.journal"
        compileSdk = 37
        minSdk = 24

        withHostTestBuilder { }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // Standardized iOS target declarations with framework configuration
    val xcfName = "dataGhostKit"
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
            implementation(libs.jetbrains.kotlinx.coroutines)
            implementation(libs.jetbrains.kotlinx.serialization.json)
            // Compose
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.ui.toolingPreview)
            implementation(libs.jetbrains.compose.components)

            // Internal Module Dependencies
            api(project(":data-ghost"))
            api(project(":data-evidence"))
            api(project(":data-trait"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Android Core & UI Libraries
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat.core)
            implementation(libs.android.material)
            // Compose UI
            implementation(libs.androidx.compose.ui.core)
            implementation(libs.androidx.compose.ui.toolingPreview)


            implementation(project(":core-resources"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain.dependencies {
            // Add iOS-specific dependencies here
        }
    }
}
