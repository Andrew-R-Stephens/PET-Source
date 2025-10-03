import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.devtools.ksp)

    alias(libs.plugins.gms.services)
}

android {
    namespace = "com.tritiumgaming.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

dependencies {

    implementation(libs.jetbrains.kotlin.stdlib)
    implementation(libs.jetbrains.kotlinx.coroutines)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.work.runtime)
    // DataStore
    implementation(libs.androidx.datastore.preferences)

    /*
     * Room DB
     */
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.ktx)
    implementation(libs.androidx.room.rxjava2)
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.room.guava)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.paging)

    // GOOGLE BILLING API
    implementation(libs.android.billing.core)

    // --- Google Credential Manager ----
    implementation(libs.android.playServices.auth)
    implementation(libs.androidx.credentials.core)
    implementation(libs.androidx.credentials.playServicesAuth)
    implementation(libs.googleid)
    // ----------------------------------

    // GOOGLE FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
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

    //GOOGLE GSON
    implementation(libs.google.gson)

    /*
     * Ktor
     */
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

    implementation(project(":shared"))
    implementation(project(":core-resources"))
    implementation(project(":core-common"))

}