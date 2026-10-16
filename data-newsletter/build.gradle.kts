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
        minSdk = 23

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
            implementation(libs.jetbrains.kotlin.stdlib)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.serialization.kotlinx.xml)

            api(project(":core-common"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            api(libs.androidx.datastore.core)
            api(libs.androidx.datastore.preferences.core)
            api(libs.jetbrains.kotlinx.coroutines)
            api(libs.jetbrains.kotlinx.serialization.core)

            // GOOGLE FIREBASE FIRESTORE
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.crashlytics.core)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.perfCore)
            // Android Core & UI Libraries
            implementation(libs.androidx.annotation)
            // Ktor Android Engines
            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.cio)

            implementation(project(":core-resources"))
        }

        getByName("androidDeviceTest") {
            dependencies {
                runtimeOnly(libs.androidx.test.runner)

                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        iosMain.dependencies {
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
        minSdk = 23

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
