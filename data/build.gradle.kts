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

    implementation(Libraries.kotlinCoroutines)

    implementation(project(Modules.core))
    implementation(project(Modules.domain))
    implementation(project(Modules.database))
    implementation(project(Modules.remote))
    api(project(Modules.model))

    implementation(Libraries.retrofit)
    implementation(Libraries.koin)

    implementation(LibrariesAndroidX.core)
    implementation(LibrariesAndroidX.livedata)


    testImplementation(Libraries.Test.core)
    testImplementation(Libraries.Test.runner)
    testImplementation(Libraries.Test.truth)
    testImplementation(Libraries.Test.truthKtx)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Test.mockk)
}
