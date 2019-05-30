plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(BuildConfigs.compileSdk)

    defaultConfig {
        minSdkVersion(BuildConfigs.minSdk)
        targetSdkVersion(BuildConfigs.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

kapt {
    correctErrorTypes = true
    mapDiagnosticLocations = true
}

dependencies {
    implementation(project(Modules.data))

    // Kotlin
    implementation(Libraries.kotlinStandardLibrary)

    implementation(LibrariesAndroidX.lifecycle)

    implementation(LibrariesAndroidX.room)
    implementation(LibrariesAndroidX.roomRuntime)
    kapt(LibrariesAndroidX.roomCompiler)

    implementation(Libraries.koin)
    implementation(Libraries.koinAndroid)

    implementation(Libraries.threetenbp)
    implementation(Libraries.threetenabp)


    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
}
