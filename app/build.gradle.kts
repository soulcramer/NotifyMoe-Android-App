plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    compileSdkVersion(BuildConfigs.compileSdk)

    defaultConfig {
        applicationId = "app.soulcramer.arn"
        minSdkVersion(BuildConfigs.minSdk)
        targetSdkVersion(BuildConfigs.targetSdk)
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resConfigs("en", "fr")
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        // Exclude AndroidX version files
        exclude("META-INF/*.version")
        // Exclude consumer proguard files
        exclude("META-INF/proguard/*")
        // Exclude the Firebase/Fabric/other random properties files
        exclude("/*.properties")
        exclude("fabric/*.properties")
        exclude("META-INF/*.properties")
    }

    buildTypes {
        getByName("debug") {
            isShrinkResources = true
            isMinifyEnabled = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Kotlin JDK
    implementation(Libraries.kotlinStandardLibrary)
    implementation(Libraries.kotlinCoroutines)

    implementation(project(Modules.core))
    implementation(project(Modules.repository))
    implementation(project(Modules.model))

    // AndroidX
    implementation(LibrariesAndroidX.activity)
    implementation(LibrariesAndroidX.fragment)
    implementation(LibrariesAndroidX.navigationFragment)
    implementation(LibrariesAndroidX.navigationUi)
    implementation(LibrariesAndroidX.recyclerView)
    implementation(LibrariesAndroidX.materialComponent)
    implementation(LibrariesAndroidX.constraintLayout)
    implementation(LibrariesAndroidX.core)
    implementation(LibrariesAndroidX.lifecycle)
    implementation(LibrariesAndroidX.emoji)

    implementation(Libraries.threetenbp)
    implementation(Libraries.threetenabp)

    // DI
    implementation(Libraries.koinAndroidXScope)
    implementation(Libraries.koinViewModel)

    implementation(Libraries.fastAdapter)
    implementation(Libraries.fastAdapterCommons)
    implementation(Libraries.fastAdapterExtensions)
    implementation(Libraries.fastAdapterExpandable)

    // Leak Canary
    debugImplementation(Libraries.leackCanary)

    implementation(Libraries.glide)
    implementation(Libraries.glideAnnotation)
    kapt(Libraries.glideCompiler)

    implementation(Libraries.glideOkhttp)
    implementation(Libraries.timberKt)
}

fun <T> propOrDef(propertyName: String, defaultValue: T): T {
    return if (hasProperty(propertyName)) property(propertyName) as T else defaultValue
}
