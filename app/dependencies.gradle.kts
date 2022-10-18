dependencies {
    //orbit mvi
    implementation(Dependencies.Mvi.core)
    implementation(Dependencies.Mvi.android)
    implementation(Dependencies.Mvi.compose)

    //coroutines
    implementation(Dependencies.Coroutines.library)

    //room db
    implementation(Dependencies.Room.library)
    implementation(Dependencies.Room.ktx)
    ksp(Dependencies.Room.ksp)

    //retrofit
    implementation(Dependencies.Retrofit.library)
    implementation(Dependencies.Retrofit.moshi)

    //compose

    //firebase
    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.firestore)
    implementation(Dependencies.Firebase.auth)

    //test
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Coroutines.test)
    testImplementation(Dependencies.Mvi.test)
}