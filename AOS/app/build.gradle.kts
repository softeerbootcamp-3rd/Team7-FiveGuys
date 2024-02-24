import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.9.21"
}

android {
    namespace = "org.softeer.robocar"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.softeer.robocar"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties().apply {
            load(File(rootDir, "local.properties").inputStream())
        }

        val kakaoNativeAppKey = properties["kakao_native_app_key"] as String
        manifestPlaceholders["kakao_native_app_key"] = kakaoNativeAppKey

        val baseUrl = properties["BASE_URL"] as String
        buildConfigField("String", "BASE_URL", baseUrl)

        val kakaoRestApiKey = properties["kakao_rest_api_key"] as String
        buildConfigField("String","kakao_rest_api_key",kakaoRestApiKey)
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

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.kakao.maps.open:android:2.6.0") // 카카오내비

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.airbnb.android:lottie:3.7.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Glide 라이브러리
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Glide를 사용하기 위한 어노테이션 프로세서
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}

kapt {
    correctErrorTypes = true
}
