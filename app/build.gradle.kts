plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.proxservices"
    compileSdk = 36 // <-- Tu requisito de API 36

    defaultConfig {
        applicationId = "com.example.proxservices"
        minSdk = 26 // <-- Tu requisito de API 26
        targetSdk = 36 // <-- Tu requisito de API 36
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // Esta versión es compatible con Kotlin 1.9.24
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ---- Dependencias Básicas (ya deberían estar) ----
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")

    // ---- BOM (Bill of Materials) - Controla las versiones de Compose ----
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // ---- Dependencias que NECESITAMOS AÑADIR ----

    // 1. Navegación en Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 2. ViewModels en Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    // 3. Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // 4. Iconos extendidos (para el ojo de "Visibility")
    implementation("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.compose.foundation)

    // ---- Dependencias de Pruebas (ya deberían estar) ----
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}