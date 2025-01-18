plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.nerodev.nerobot"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nerodev.nerobot"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "NEWS_API_KEY", "\"a248ae547ea44294886d9aab53d78ed0\"")
        buildConfigField("String", "API_KEY", "\"AIzaSyBURdIcSeAD_ahQP-KPJDINxG8UHwD2bzw\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.material3)
    implementation (libs.ui)
    implementation (libs.androidx.foundation)
    implementation (libs.androidx.material)
    implementation (libs.androidx.documentfile)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui.test.junit4.android)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Coil
    implementation (libs.coil.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Location
    implementation (libs.play.services.location)
    implementation (libs.maps.compose)
    implementation (libs.play.services.maps)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    implementation(libs.glide.compose)

    // Gmaps
    implementation(libs.maps.compose)
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)

    // Serialization JSON
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.gson)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)

    // Data Store
    implementation (libs.androidx.datastore.preferences)
    implementation (libs.androidx.datastore.core)

    //Coroutine
    implementation (libs.kotlinx.coroutines.android)

    //Testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.core.testing)

    //Moshi
    implementation (libs.moshi)
    implementation (libs.moshi.kotlin)

    ksp (libs.moshi.kotlin.codegen)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

}