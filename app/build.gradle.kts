plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("plugin.serialization") version "2.1.21"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.carbondev.carboncheck"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.carbondev.carboncheck"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "secrets.defaults.properties"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Testing dependencies (JUnit, MockK)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Supabase dependencies
    implementation(platform(libs.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.auth.kt)
    implementation(libs.realtime.kt)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.serializer.moshi)

    // Moshi dependencies
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    ksp(libs.moshi.kotlin.codegen)

    // Hilt dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Used for Hilt navigation, do not install the other navigation library

    // Timber dependencies
    implementation(libs.timber) // Timber is a logger with a small, extensible API which provides utility on top of Android's normal Log class.

    // Coil dependencies, used for image loading from remote sources.
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Proto DataStore dependencies, used for storing user preferences as an alternative to SharedPreferences.
    implementation(libs.androidx.datastore)

    // Retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)

    // Konform dependencies for validation
    implementation(libs.konform.jvm)

    implementation(libs.ycharts)

//    implementation(libs.androidx.navigation.compose)
}

