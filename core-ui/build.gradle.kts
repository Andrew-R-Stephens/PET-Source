plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.gms.services)
}

kotlin {

    // Target declarations
    android {
        namespace = "com.tritiumgaming.core.ui"
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
    val xcfName = "coreUiKit"

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
                api(libs.jetbrains.kotlinx.coroutines)

                implementation(libs.jetbrains.kotlin.stdlib)
                implementation(libs.jetbrains.compose.components)

                // Internal Module Dependencies
                api(project(":core-common"))
                api(project(":data-language"))
                api(project(":data-palette"))
                api(project(":data-preferences"))
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
                api(libs.androidx.compose.foundation)
                api(libs.androidx.compose.foundation.layout)
                api(libs.androidx.compose.material3)
                api(libs.androidx.compose.runtime)
                api(libs.androidx.compose.ui.core)
                api(libs.androidx.compose.ui.graphics)

                implementation(libs.androidx.activity)
                implementation(libs.androidx.annotation)
                implementation(libs.androidx.compose.animation.core)
                implementation(libs.androidx.compose.animation)
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.compose.runtime.annotation)
                implementation(libs.androidx.core)



                implementation(libs.androidx.compose.ui.toolingPreview)
                implementation(libs.androidx.lifecycle.runtime.compose)

                implementation(libs.android.playServices.ads)

            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                /*implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)*/
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here
            }
        }
    }
}
