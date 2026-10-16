plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.gms.services)
}

kotlin {
    android {
        namespace = "com.tritiumgaming.feature.start"
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

    val xcfName = "FeatureStart"
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.kotlinx.coroutines)

            implementation(libs.jetbrains.kotlin.stdlib)
            implementation(libs.jetbrains.compose.components)

            api(project(":core-common"))

            api(project(":data-challenge"))
            api(project(":data-newsletter"))
            api(project(":data-preferences"))
            api(project(":data-review"))
            implementation(project(":data-palette"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.androidx.compose.foundation.layout)
            api(libs.androidx.compose.runtime)
            api(libs.androidx.appcompat.core)
            api(libs.androidx.compose.foundation)
            api(libs.androidx.compose.ui.core)

            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.annotation)
            implementation(libs.androidx.window.core)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material3.adaptive)
            implementation(libs.androidx.compose.ui.toolingPreview)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.compose.ui.graphics)

            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.crashlytics.core)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.perfCore)

            // Google Ads & Review
            implementation(libs.android.playServices.ads)
            implementation(libs.android.play.core.review)

            // Core Resources & UI
            //implementation(project(":core-resources"))
            implementation(project(":core-ui"))
            implementation(project(":data-typography"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
                runtimeOnly(libs.androidx.test.runner)
                runtimeOnly(libs.androidx.core)
            }
        }

        iosMain.dependencies {
            // iOS specific dependencies if needed
        }
    }
}
