import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.core"
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

    api(libs.androidx.datastore.core)
    api(libs.androidx.datastore.preferences.core)

    implementation(libs.jetbrains.kotlinx.coroutines)

    // GOOGLE FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.functions)
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)

    androidTestRuntimeOnly(libs.androidx.test.runner)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)


    api(project(":core-common"))
    //api(project(":core-ui"))

    api(project(":data-account"))
    api(project(":data-ads"))
    api(project(":data-challenge"))
    api(project(":data-preferences"))
    api(project(":data-language"))
    api(project(":data-marketplace"))
    api(project(":data-newsletter"))
    api(project(":data-operation"))
    api(project(":data-review"))
    api(project(":data-customdifficulty"))
    api(project(":data-wearable"))
    api(project(":data-policy"))
    api(project(":database-local"))

    implementation(project(":data-mission"))
    implementation(project(":data-typography"))
    implementation(project(":data-palette"))
}