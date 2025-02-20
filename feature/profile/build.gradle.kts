plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "yandex.practicum.workshop.profile"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:analytics"))

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}