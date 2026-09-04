import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.library)
}

configure<LibraryExtension> {
    namespace = "com.tritiumgaming.data.ads"
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
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.android.playServices.ads)
    implementation(project(":shared"))
}
