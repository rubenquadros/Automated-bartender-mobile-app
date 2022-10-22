plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId  = "com.ruben.bartender"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = Versions.compose
        }

        buildFeatures {
            compose = true
        }

        testOptions {
            unitTests {
                isReturnDefaultValues = true
                isIncludeAndroidResources = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    signingConfigs {
        register("release").configure {
            keyAlias = "bartender"
            keyPassword = "bartender"
            storeFile = file("$projectDir/keystore/app.jks")
            storePassword = "bartender"
        }

        named("debug").configure {
            keyAlias = "bartender"
            keyPassword = "bartenderdebug"
            storeFile = file("$projectDir/keystore/app-debug.jks")
            storePassword = "bartenderdebug"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.findByName("release")
            isDebuggable = true
            isMinifyEnabled =  true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            isDebuggable = true
            isMinifyEnabled =  false
            signingConfig = signingConfigs.findByName("debug")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.ruben.bartender"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    //orbit mvi
    implementation(Dependencies.Mvi.core)
    implementation(Dependencies.Mvi.android)
    implementation(Dependencies.Mvi.compose)

    //androidX
    implementation(Dependencies.Androidx.splashScreen)

    //material design
    implementation(Dependencies.Material.library)

    //coroutines
    implementation(Dependencies.Coroutines.library)

    //room db
    implementation(Dependencies.Room.library)
    implementation(Dependencies.Room.ktx)
    kapt(Dependencies.Room.kapt)

    //retrofit
    implementation(Dependencies.Retrofit.library)
    implementation(Dependencies.Retrofit.moshi)

    //compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.fonts)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.materialWindow)
    implementation(Dependencies.Compose.toolingPreview)
    debugImplementation(Dependencies.Compose.uiTooling)
    debugImplementation(Dependencies.Compose.uiTest)
    implementation(Dependencies.Compose.viewmodel)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.lifecycle)
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.constraintLayout)
    implementation(Dependencies.Compose.coil)

    //accompanist
    implementation(Dependencies.Accompanist.navigationAnimation)

    //hilt
    implementation(Dependencies.Hilt.library)
    implementation(Dependencies.Hilt.compose)
    kapt(Dependencies.Hilt.kapt)

    //firebase
    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.firestore)
    implementation(Dependencies.Firebase.auth)

    //test
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Coroutines.test)
    testImplementation(Dependencies.Mvi.test)

    //remove these
            implementation("androidx.appcompat:appcompat:1.1.0")
            implementation("androidx.constraintlayout:constraintlayout:1.1.3")
}
