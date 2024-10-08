plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.moronlu18.helloworldkotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moronlu18.helloworldkotlin"
        minSdk = 33
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
//Crea la documentación en una carpeta fuera de /app llamada /documentation/html
tasks.dokkaHtml.configure {
    outputDirectory.set(file("../documentation/html"))
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

    //Librerías para trabajar con Firebase
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics.ktx)

    dokkaPlugin(libs.dokka.documentation )
    // Is applied universally
    dokkaPlugin(libs.dokka.mathjax)
    // Is applied for the single-module dokkaHtml task only
    dokkaHtmlPlugin(libs.dokka.kotlin)
    // Is applied for HTML format in multi-project builds
    dokkaHtmlPartialPlugin(libs.dokka.kotlin)
}