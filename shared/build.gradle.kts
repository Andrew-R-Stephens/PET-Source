import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)

    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {

    android {
        namespace = "com.tritiumgaming.shared"
        compileSdk = 37
        minSdk = 23

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
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

    val xcfName = "sharedKit"

    val iosTargets = listOf(
        iosArm64(),
        iosSimulatorArm64()
    )

    iosTargets.forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true

            export(project(":core-common"))
            export(project(":core-ui"))
            export(project(":data-account"))
            export(project(":data-ads"))
            export(project(":data-challenge"))
            export(project(":data-codex"))
            export(project(":data-contributor"))
            export(project(":data-customdifficulty"))
            export(project(":data-difficulty"))
            export(project(":data-difficultysetting"))
            export(project(":data-equipment"))
            export(project(":data-evidence"))
            export(project(":data-ghost"))
            export(project(":data-ghostbox"))
            export(project(":data-ghostname"))
            export(project(":data-investigation"))
            export(project(":data-journal"))
            export(project(":data-language"))
            export(project(":data-map"))
            export(project(":data-marketplace"))
            export(project(":data-mission"))
            export(project(":data-newsletter"))
            export(project(":data-operation"))
            export(project(":data-palette"))
            export(project(":data-phase"))
            export(project(":data-policy"))
            export(project(":data-preferences"))
            export(project(":data-review"))
            export(project(":data-sanity"))
            export(project(":data-temperature"))
            export(project(":data-trait"))
            export(project(":data-typography"))
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.jetbrains.compose.runtime)
                implementation(libs.jetbrains.compose.foundation)
                implementation(libs.jetbrains.compose.material3)
                implementation(libs.androidx.compose.ui.core)
                implementation(libs.jetbrains.compose.components)
                implementation(libs.androidx.compose.ui.tooling)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtime.compose)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.compose.ui.toolingPreview)
                implementation(libs.androidx.compose.ui.tooling)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {

            }
        }

        iosMain {
            dependencies {
                api(project(":core-common"))
                api(project(":core-ui"))
                api(project(":data-account"))
                api(project(":data-ads"))
                api(project(":data-challenge"))
                api(project(":data-codex"))
                api(project(":data-contributor"))
                api(project(":data-customdifficulty"))
                api(project(":data-difficulty"))
                api(project(":data-difficultysetting"))
                api(project(":data-equipment"))
                api(project(":data-evidence"))
                api(project(":data-ghost"))
                api(project(":data-ghostbox"))
                api(project(":data-ghostname"))
                api(project(":data-investigation"))
                api(project(":data-journal"))
                api(project(":data-language"))
                api(project(":data-map"))
                api(project(":data-marketplace"))
                api(project(":data-mission"))
                api(project(":data-newsletter"))
                api(project(":data-operation"))
                api(project(":data-palette"))
                api(project(":data-phase"))
                api(project(":data-policy"))
                api(project(":data-preferences"))
                api(project(":data-review"))
                api(project(":data-sanity"))
                api(project(":data-temperature"))
                api(project(":data-trait"))
                api(project(":data-typography"))
            }
        }

    }

}

dependencies {
    androidRuntimeClasspath(libs.androidx.compose.ui.tooling)
}