plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "yandex.practicum.middle.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "yandex.practicum.middle.android.bluetooth"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "type"
    productFlavors {
        create("teach") {
            dimension = "type"
        }
        create("task") {
            isDefault = true
            dimension = "type"
        }
        create("result") {
            dimension = "type"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.scanner)
    implementation(libs.client)
    implementation(libs.advertiser)
    implementation(libs.server.mock)
    implementation(libs.mock)
    implementation(libs.server.api)
    implementation(libs.client.api)
    implementation(libs.client.mock)

    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
