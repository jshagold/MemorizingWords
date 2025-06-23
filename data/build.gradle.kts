import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

val propertiesFile = project(":data").file("data.properties")
val dataProperties = Properties().apply {
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use {
            load(it)
        }
    }
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "SELVAS_TTS_HOST", dataProperties["SELVAS_TTS_HOST"].toString())
        buildConfigField("String", "SELVAS_TTS_PORT", dataProperties["SELVAS_TTS_PORT"].toString())
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
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)
    implementation(projects.database)

    // Selvas TTS
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("pttsnet_class.jar"))))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // Napier Log
    implementation(libs.napier)

    // JSON Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

}