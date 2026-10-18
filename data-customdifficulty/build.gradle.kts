plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.devtools.ksp)
}

kotlin {
    // ------------------------------------------------------------------------
    // Target Declarations
    // ------------------------------------------------------------------------

    android {
        namespace = "com.tritiumgaming.data.customdifficulty"
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
    val xcfName = "dataCustomDifficultyKit"
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
            api(libs.jetbrains.kotlinx.coroutines)
            implementation(libs.jetbrains.kotlin.stdlib)
            api(project(":data-difficultysetting"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.annotation)

            implementation(libs.google.gson)

            // Room Database (Android Native)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            //api(project(":core-resources"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                runtimeOnly(libs.androidx.test.runner)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain.dependencies {
            // Add iOS-specific dependencies here
        }
    }
}

// KSP configuration for Room annotation processor
dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
}
