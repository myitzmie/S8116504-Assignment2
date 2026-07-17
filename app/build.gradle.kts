plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.s8116504.assignment2"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.s8116504.assignment2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    // Testing
    testImplementation("io.mockk:mockk:1.14.11")
    testImplementation("io.mockk:mockk-android:1.14.11")
    testImplementation("io.mockk:mockk-agent:1.14.11")
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Retrofit (API calls)
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")
    // View Model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.11.0")
    //Hilt
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")
    //Recycleview
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    //Material
    implementation("com.google.android.material:material:1.14.0")
    //Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.8")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.8")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

}

