plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

apply("./gradleScript/dependencies.gradle")

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
}
