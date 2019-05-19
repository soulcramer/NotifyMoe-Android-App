plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(BuildConfigs.compileSdk)


    defaultConfig {
        minSdkVersion(BuildConfigs.minSdk)
        targetSdkVersion(BuildConfigs.targetSdk)
        versionCode = 1
        versionName = "1.0"

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

dependencies {
    implementation(Libraries.kotlinStandardLibrary)

    implementation(Libraries.koin)
    implementation(Libraries.gson)

    implementation(LibrariesAndroidX.roomRuntime)

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
}
