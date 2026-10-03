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
        minSdk = 24

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
            implementation(libs.jetbrains.kotlin.stdlib)
            implementation(libs.jetbrains.kotlinx.coroutines)
            implementation(libs.jetbrains.kotlinx.serialization.json)

            // Compose Runtime
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.ui.toolingPreview)
            implementation(libs.jetbrains.compose.components)

            api(project(":core-common"))

            implementation(project(":data-challenge"))
            implementation(project(":data-newsletter"))
            implementation(project(":data-palette"))
            implementation(project(":data-preferences"))
            implementation(project(":data-review"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat.core)
            implementation(libs.android.material)
            implementation(libs.androidx.navigation.compose)

            implementation(libs.androidx.compose.foundation)
            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material3.adaptive)
            implementation(libs.androidx.compose.ui.core)
            implementation(libs.androidx.compose.ui.toolingPreview)

            implementation(libs.androidx.activityCompose)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.ui.graphics)

            // DataStore
            implementation(libs.androidx.datastore.preferences)

            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.crashlytics.core)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.perfCore)

            // Google Ads & Review
            implementation(libs.android.playServices.ads)
            implementation(libs.android.ump.core)
            implementation(libs.android.play.core.review)
            implementation(libs.android.play.coreKtx.review)

            // Core Resources & UI
            implementation(project(":core-resources"))
            implementation(project(":core-ui"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain.dependencies {
            // iOS specific dependencies if needed
        }
    }
}
