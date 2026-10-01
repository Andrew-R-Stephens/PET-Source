import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.feature.core"
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

    // GOOGLE FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // GOOGLE FIREBASE AUTH
    implementation(libs.firebase.auth)
    // GOOGLE FIREBASE FIRESTORE
    implementation(libs.firebase.firestore)
    // GOOGLE FIREBASE FUNCTIONS
    implementation(libs.firebase.functions)
    // GOOGLE FIREBASE ANALYTICS
    implementation(libs.firebase.analytics)

    

    api(project(":core-common"))
    api(project(":core-ui"))

    api(project(":data-account"))
    api(project(":data-ads"))
    api(project(":data-challenge"))
    api(project(":data-mission"))
    api(project(":data-preferences"))
    api(project(":data-language"))
    api(project(":data-marketplace"))
    api(project(":data-newsletter"))
    api(project(":data-operation"))
    api(project(":data-typography"))
    api(project(":data-palette"))
    api(project(":data-review"))
    api(project(":data-customdifficulty"))
    api(project(":data-wearable"))
    api(project(":data-policy"))
    api(project(":database-local"))
}