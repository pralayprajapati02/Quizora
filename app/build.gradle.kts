plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.quizora"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quizora"
        minSdk = 26
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
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Picasso
    implementation (libs.picasso)
    //Retrofit
    implementation(libs.retrofit)
    //GSON Convertor
    implementation (libs.converter.gson)
    //Coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)
    //Generative AI
    implementation (libs.generativeai)
    //lottie Files
    implementation (libs.lottie)
    //ShimmerEffect
    implementation (libs.shimmer)
    //Skydoves Balloon
    implementation(libs.balloon)
    //Splash API
    implementation (libs.androidx.core.splashscreen)

}