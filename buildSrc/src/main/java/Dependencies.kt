/**
 * Created by Ruben Quadros on 19/10/22
 **/
object Versions {
    const val compileSdk = 33
    const val targetSdk = 33
    const val minSdk = 21
    const val versionCode = 1
    const val versionName = "1.0"
    const val compose = "1.3.0-rc01"
}

object GradlePlugin {
    const val gradle = "com.android.tools.build:gradle:7.3.1"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10"
    const val googleServices = "com.google.gms:google-services:4.3.14"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.9.2"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:2.44"
}

object Dependencies {
    object Androidx {
        const val splashScreen = "androidx.core:core-splashscreen:1.0.0"
    }

    object Compose {
        private const val version = "1.3.0-rc01"
        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"

        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha02"
        const val navigation = "androidx.navigation:navigation-compose:2.6.0-alpha02"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha02"
        const val activity = "androidx.activity:activity-compose:1.7.0-alpha01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        const val coil = "io.coil-kt:coil-compose:2.0.0"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val library = "com.squareup.retrofit2:retrofit:$version"
        const val moshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Room {
        private const val version = "2.4.3"
        const val library = "androidx.room:room-runtime:$version"
        const val ktx = "androidx.room:room-ktx:$version"
        const val ksp = "androidx.room:room-compiler:$version"
    }

    object Hilt {
        private const val version = "2.44"
        const val library = "com.google.dagger:hilt-android:$version"
        const val kapt = "com.google.dagger:hilt-android-compiler:$version"
        const val compose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object Coroutines {
        private const val version = "1.6.4"
        const val library = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val mockk = "io.mockk:mockk:1.13.2"
    }

    object GooglePay {

    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:31.0.0"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val auth = "com.google.firebase:firebase-auth-ktx"
    }

    object Mvi {
        private const val version = "4.4.0"
        const val core = "org.orbit-mvi:orbit-core:$version"
        const val android = "org.orbit-mvi:orbit-viewmodel:$version"
        const val compose = "org.orbit-mvi:orbit-compose:$version"
        const val test = "org.orbit-mvi:orbit-test:$version"
    }
}