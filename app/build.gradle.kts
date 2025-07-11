import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleDaggerHiltAndroid)
}

android {
    namespace = "com.example.memorizingwords"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.memorizingwords"
        minSdk = 28
        targetSdk = 35
        versionCode = 4
        versionName = "1.3.0"

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
    }

    packaging {
        resources.excludes.add("META-INF/INDEX.LIST")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.junit.runner)
    androidTestImplementation(libs.androidx.junit.runner)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // Navigation-Compose
    implementation(libs.navigation.compose)

    // ConstraintLayout-Compose
    implementation(libs.androidx.constraintlayout.compose)


    // Napier Log
    implementation(libs.napier)

    // Paging
    implementation(libs.paging)
    implementation(libs.paging.compose)

    // Media3 ExoPlayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.ui)
    implementation(libs.media3.ui.compose)


    implementation("io.appium:java-client:9.4.0")
//    implementation("org.seleniumhq.selenium:selenium-java:4.28.0")
}

tasks.withType<Test>().configureEach {

    useJUnit()

    isScanForTestClasses = false

    include("**/AppiumSuite.class")

}