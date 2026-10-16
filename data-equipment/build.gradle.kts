plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.android.lint)
}

kotlin {

    android {
        namespace = "com.tritiumstudios.data.equipment"
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

    val xcfName = "data-equipmentKit"

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
                implementation(libs.jetbrains.kotlin.stdlib)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
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