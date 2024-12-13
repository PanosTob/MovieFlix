plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.serialization)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.panostob.movieflix"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.panostob.movieflix"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions += listOf("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            buildConfigField("String", "TMDB_JSON_API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "TMDB_JSON_API_IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
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
    implementation(libs.androidx.appcompat.resources)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Compose Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.compose)
    //Lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Dependency Injection
    implementation(libs.google.hilt)
    ksp(libs.google.hilt.compiler)

    //Pagination
    implementation(libs.androidx.paging.compose)
    //Image Loading
    implementation(libs.coil.coil3.compose.android)
    implementation(libs.coil.coil3.okhttp)

    //Network
    implementation(libs.squareup.moshi)
    ksp(libs.squareup.moshi.codegen)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)


    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.okhttp3.logging)
    implementation(libs.squareup.okhttp3.sse)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.moshi)

    implementation(libs.jetbrains.kotlinx.serialization.json)

    implementation(libs.androidx.security.crypto.ktx)
}