plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val composeVersion: String by rootProject.extra
val daggerVersion: String by rootProject.extra
val roomVersion: String by rootProject.extra
val jUnitVersion: String by rootProject.extra
val androidJUnitExtension: String by rootProject.extra

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.notesapp"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

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
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
        kotlinCompilerVersion = "1.5.21"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    implementation(project(":data"))
    implementation(project(":domain"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.2")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:1.4.0")

    // Testing
    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidJUnitExtension")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")

    // Hilt
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerVersion")

    // Room components
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")
}