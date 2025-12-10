import java.util.Properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}
android {
    namespace = "com.example.paseleriamilsabores"
    compileSdk = 36
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.example.paseleriamilsabores"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

// Configuración para acceder al token de Mapbox en el código Kotlin
        buildConfigField(
            "String",
            "MAPBOX_PUBLIC_TOKEN",
            "\"${localProperties.getProperty("MAPBOX_PUBLIC_TOKEN") ?: ""}\""
        )
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
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

// Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")

// Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material:material-icons-extended:<version>")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

// --- Fundamentales de Compose (Usando BOM para versiones) ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
// --- 1. Navegación (SOLUCIÓN al error de import) ---
// Usamos la dependencia explícita en lugar de 'libs.androidx.navigation.compose'
    implementation("androidx.navigation:navigation-compose:2.7.0")
// --- 2. Geolocalización (FusedLocationProviderClient de Google) ---
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")
// --- 3. Mapbox (SOLUCIÓN al error de import, usando las librerías estándar) ---
    implementation("com.mapbox.maps:android:11.16.2")
    implementation("com.mapbox.extension:maps-compose:11.16.2")
// --- 4. Fuentes de Google ---
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.8")
// --- 5. Persistencia JSON (Gson) ---
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.androidx.compose.runtime)

// --- Testing ---
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("app.cash.turbine:turbine:1.0.0")
// JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
tasks.withType<Test> {
    useJUnitPlatform()
}