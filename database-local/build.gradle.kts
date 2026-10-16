import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.devtools.ksp)
}

configure<LibraryExtension> {

    namespace = "com.tritiumgaming.database.local"
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

    api(libs.jetbrains.kotlinx.coroutines)
    api(libs.androidx.room.runtime)

    ksp(libs.androidx.room.compiler)

    implementation(libs.google.gson)

    androidTestRuntimeOnly(libs.androidx.test.core)
    androidTestRuntimeOnly(libs.androidx.test.runner)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":data-difficultysetting"))
    api(project(":data-customdifficulty"))
}