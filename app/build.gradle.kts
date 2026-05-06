plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.cit.pointage"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.cit.pointage"
        minSdk = 24
        targetSdk = 36
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

    // ════════ VIEWBINDING ════════
    // Accès aux vues XML sans findViewById
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.activity.ktx)
    // ════════ ANDROID DE BASE ════════
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // ════════ VIEWMODEL + LIVEDATA ════════
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")

    // ════════ NAVIGATION ════════
    implementation("androidx.navigation:navigation-fragment:2.8.9")
    implementation("androidx.navigation:navigation-ui:2.8.9")

    // ════════ RETROFIT (appels HTTP) ════════
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // ════════ OKHTTP (logs HTTP) ════════
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ════════ GSON (JSON) ════════
    implementation("com.google.code.gson:gson:2.11.0")

    // ════════ SCAN QR CODE ════════
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    // ════════ CHARGEMENT IMAGES ════════
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // ════════ TESTS ════════
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}