plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)

    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    android {
        namespace = "com.tritiumgaming.shared"
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

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "sharedKit"

    val iosTargets = listOf(
        iosArm64(),
        iosSimulatorArm64()
    )

    iosTargets.forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName

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

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                /*implementation(libs.jetbrains.kotlin.stdlib)
                implementation(libs.jetbrains.kotlinx.coroutines)
                implementation(libs.jetbrains.kotlinx.serialization.json)
                // Compose
                implementation(libs.jetbrains.compose.runtime)
                implementation(libs.jetbrains.compose.foundation)
                implementation(libs.jetbrains.compose.material3)
                implementation(libs.jetbrains.compose.ui)
                implementation(libs.jetbrains.compose.ui.toolingPreview)
                implementation(libs.jetbrains.compose.components)*/
            }
        }

        commonTest {
            dependencies {
                //implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain.

                // Compose UI
                /*implementation(libs.androidx.compose.ui.core)
                implementation(libs.androidx.compose.ui.toolingPreview)*/

                /*Optional - Included automatically by material, only add when you need
                the icons but not the material library (e.g. when using Material3 or a
                custom design system based on Foundation)*/
                /*implementation(libs.androidx.compose.runtime.liveData) // Optional - Integration with LiveData
                implementation(libs.androidx.compose.runtime.rxJava2) // Optional - Integration with RxJava

                implementation(libs.androidx.activityCompose)
                implementation(libs.androidx.lifecycle.runtime.ktx)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.navigation.compose)*/

                // WEARABLE
                /*implementation(libs.android.playServices.wearable)
                implementation(libs.jetbrains.kotlinx.coroutines.play.services)*/

                // GOOGLE FIREBASE FIRESTORE
                /*implementation(project.dependencies.platform(libs.firebase.bom))*/
                // GOOGLE FIREBASE AUTH
                /*implementation(libs.firebase.auth)*/
                // GOOGLE FIREBASE FIRESTORE
                /*implementation(libs.firebase.firestore)*/
                // Declare the dependencies for the Crashlytics and Analytics libraries
                // When using the BoM, you don't specify versions in Firebase library dependencies
                // GOOGLE FIREBASE ANALYTICS
                /*implementation(libs.firebase.crashlytics.core)
                implementation(libs.firebase.analytics)
                implementation(libs.firebase.perfCore)*/
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                /*implementation(libs.androidx.runner)
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)*/
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