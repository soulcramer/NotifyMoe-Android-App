import com.android.build.gradle.internal.dsl.TestOptions

plugins {
    id("com.android.library")
    kotlin("android")
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
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            setIncludeAndroidResources(true)
        })
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

}

dependencies {
    implementation(project(Modules.model))
    implementation(Libraries.kotlinStandardLibrary)
    implementation(Libraries.kotlinCoroutines)

    implementation(LibrariesAndroidX.livedata)

    implementation(Libraries.timberKt)

    testImplementation(Libraries.Test.core)
    testImplementation(Libraries.Test.runner)
    testImplementation(Libraries.Test.truth)
    testImplementation(Libraries.Test.truthKtx)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Test.mockk)

}
