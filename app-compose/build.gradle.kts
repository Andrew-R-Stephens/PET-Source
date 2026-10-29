import com.android.build.api.dsl.ApplicationExtension

plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)

    alias(libs.plugins.gms.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)

    alias(libs.plugins.devtools.ksp)
}

configure<ApplicationExtension> {

    namespace = "com.tritiumgaming.core"
    compileSdk = 37

    /*
     *  Compose Options
     *  ----------------
    */
    buildFeatures {
        compose = true
        buildConfig = true
    }

    /* ---------------- */

    bundle {
        language {
            enableSplit = false
        }
    }

    defaultConfig {

        applicationId = "com.TritiumGaming.phasmophobiaevidencepicker"

        minSdk = 24
        targetSdk = 37
        versionCode = 205
        versionName = "1.0.3"

        ndk {
            abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testOptions {
            unitTests {
                isReturnDefaultValues = true
            }
        }

        vectorDrawables.useSupportLibrary = true

        buildConfigField("boolean", "USE_NEWSLETTER", "true")
        buildConfigField("boolean", "USE_FIRESTORE", "true")
        buildConfigField("boolean", "USE_ACCOUNT", "true")
        buildConfigField("boolean", "USE_MARKETPLACE", "true")
    }

    /*flavorDimensions += "minSdk"

    productFlavors {
        create("legacy") {
            dimension = "minSdk"
            minSdk = 23
            versionCode = 206
        }
        create("standard") {
            dimension = "minSdk"
            minSdk = 24
            versionCode = 207
        }
    }*/

    buildTypes {
        debug {
            isMinifyEnabled = false
            //noinspection NotShrinkingResources
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
        create("releaseTest") {
            initWith(getByName("release"))
            buildConfigField("boolean", "USE_NEWSLETTER", "false")
            buildConfigField("boolean", "USE_FIRESTORE", "false")
            buildConfigField("boolean", "USE_ACCOUNT", "false")
            buildConfigField("boolean", "USE_MARKETPLACE", "false")
        }
    }

    buildToolsVersion = "36.1.0"

}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles.addAll(
        rootProject.layout.projectDirectory.file("stability_config.conf")
    )
}

dependencies {

    ksp(libs.androidx.room.compiler)

    implementation("androidx.compose.ui:ui-text:1.12.1")
    implementation("androidx.compose.ui:ui-unit:1.12.1")
    implementation("androidx.datastore:datastore:1.2.1")
    implementation("androidx.lifecycle:lifecycle-common:2.11.0")
    implementation("androidx.navigation:navigation-common:2.10.2")
    implementation("androidx.navigation:navigation-runtime:2.10.2")

    // PRIMARY
    implementation(libs.jetbrains.kotlin.stdlib)
    implementation(libs.jetbrains.kotlinx.coroutines)

    // GOOGLE FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.functions)
    implementation(libs.firebase.crashlytics.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perfCore)

    // noinspection LoginCredentials
    implementation(libs.android.playServices.auth)
    runtimeOnly(libs.android.billing.core)
    implementation(libs.android.ump.core)
    implementation(libs.android.play.core.update)
    implementation(libs.android.playServices.wearable)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences.core)
    runtimeOnly(libs.androidx.work.runtime)
    implementation(libs.androidx.appcompat.core)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.ui.toolingPreview)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.room.runtime)
    // noinspection LoginCredentials
    runtimeOnly(libs.androidx.credentials.playServicesAuth)


    implementation(libs.coilKt.network)
    runtimeOnly(libs.coilKt.gif)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.cio)
    runtimeOnly(libs.droidsonroids.gifDrawable)
    implementation(libs.realm.kotlin.library.sync)


    annotationProcessor(libs.androidx.room.ktx)


    /* TESTING */

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    androidTestRuntimeOnly(libs.androidx.test.runner)

    testImplementation(libs.junit)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)

    debugRuntimeOnly(libs.androidx.compose.ui.testManifest)
    debugImplementation(libs.androidx.compose.ui.tooling)


    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.play.services)
    androidTestImplementation("androidx.test:monitor:1.8.0")
    androidTestImplementation("org.jetbrains:annotations:23.0.0")

    androidTestRuntimeOnly(libs.androidx.test.core)

    testImplementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.mockito:mockito-core:5.23.0")

    /* LOCAL DEPENDENCIES */

    implementation(project(":core-common"))
    implementation(project(":core-resources"))
    implementation(project(":core-ui"))

    implementation(project(":database-local"))

    implementation(project(":data-challenge"))
    implementation(project(":data-customdifficulty"))
    implementation(project(":data-language"))
    implementation(project(":data-review"))
    implementation(project(":data-account"))
    implementation(project(":data-ads"))
    implementation(project(":data-codex"))
    implementation(project(":data-difficulty"))
    implementation(project(":data-difficultysetting"))
    implementation(project(":data-evidence"))
    implementation(project(":data-journal"))
    implementation(project(":data-map"))
    implementation(project(":data-marketplace"))
    implementation(project(":data-newsletter"))
    implementation(project(":data-operation"))
    implementation(project(":data-palette"))
    implementation(project(":data-policy"))
    implementation(project(":data-preferences"))
    implementation(project(":data-typography"))
    implementation(project(":data-wearable"))

    implementation(project(":feature-core"))
    implementation(project(":feature-start"))
    implementation(project(":feature-account"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-language"))
    implementation(project(":feature-home"))
    implementation(project(":feature-about"))
    implementation(project(":feature-newsletter"))
    implementation(project(":feature-marketplace"))
    implementation(project(":feature-codex"))
    implementation(project(":feature-investigation"))
    implementation(project(":feature-customdifficulty"))
    implementation(project(":feature-mapviewer"))
    implementation(project(":feature-objectiveboard"))
    implementation(project(":feature-operation"))

}
