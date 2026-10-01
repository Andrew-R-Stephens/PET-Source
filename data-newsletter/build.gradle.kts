plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    // ------------------------------------------------------------------------
    // Target Declarations
    // ------------------------------------------------------------------------

    android {
        namespace = "com.tritiumgaming.data.newsletter"
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
    val xcfName = "dataNewsletterKit"
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
            // Kotlin Standard Library & Serialization
            implementation(libs.jetbrains.kotlin.stdlib)

            // Jetpack DataStore
            implementation(libs.androidx.datastore.preferences)

            // Ktor Core & Content Negotiation
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)

            // Ktor Serializers
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.serialization.kotlinx.xml)
            implementation(libs.ktor.serialization.kotlinx.cbor)
            implementation(libs.ktor.serialization.kotlinx.protobuf)

            api(project(":core-common"))

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Add Android-specific dependencies here. Note that this source set depends on
            // commonMain by default and will correctly pull the Android artifacts of any KMP
            // dependencies declared in commonMain.

            // Compose UI
            implementation(libs.androidx.compose.ui.core)
            implementation(libs.androidx.compose.ui.toolingPreview)

            /*Optional - Included automatically by material, only add when you need
            the icons but not the material library (e.g. when using Material3 or a
            custom design system based on Foundation)*/
            implementation(libs.androidx.compose.runtime.liveData) // Optional - Integration with LiveData
            implementation(libs.androidx.compose.runtime.rxJava2) // Optional - Integration with RxJava

            implementation(libs.androidx.activityCompose)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)

            // WEARABLE
            implementation(libs.android.playServices.wearable)
            implementation(libs.jetbrains.kotlinx.coroutines.play.services)

            // GOOGLE FIREBASE FIRESTORE
            implementation(project.dependencies.platform(libs.firebase.bom))
            // GOOGLE FIREBASE AUTH
            implementation(libs.firebase.auth)
            // GOOGLE FIREBASE FIRESTORE
            implementation(libs.firebase.firestore)
            // Declare the dependencies for the Crashlytics and Analytics libraries
            // When using the BoM, you don't specify versions in Firebase library dependencies
            // GOOGLE FIREBASE ANALYTICS
            implementation(libs.firebase.crashlytics.core)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.perfCore)
            // Android Core & UI Libraries
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat.core)
            implementation(libs.android.material)

            // Ktor Android Engines
            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.cio)

            implementation(project(":core-resources"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain.dependencies {
            // Add iOS Ktor engine here if needed (e.g., libs.ktor.client.darwin)
        }
    }
}

/*
import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
    // alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.jetbrains.kotlin.serialization)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.data.newsletter"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }


    buildTypes {
        debug {
            initWith(getByName("debug"))
        }
        release {
            initWith(getByName("release"))
        }
        create("releaseTest") {
            initWith(getByName("releaseTest"))
        }
    }


    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "36.1.0"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    */
/*
     * Ktor
     *//*

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.kotlinx.xml)
    implementation(libs.ktor.serialization.kotlinx.cbor)
    implementation(libs.ktor.serialization.kotlinx.protobuf)
    implementation(libs.ktor.client.logging)

    
    implementation(project(":core-common"))
    implementation(project(":core-resources"))
}*/
