import com.android.build.gradle.internal.dsl.TestOptions

plugins {
    id("com.android.application")
    id("jacoco")
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
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            isTestCoverageEnabled = true
        }
    }

    testOptions {
        execution = "ANDROID_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            setIncludeAndroidResources(true)
        })
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
    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.cache))
    implementation(project(Modules.remote))

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


jacoco {
    toolVersion = "0.8.0"
}

tasks.withType(Test::class.java) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {

    dependsOn("testDebugUnitTest")
    group = "reporting"
    description = "Generate Jacoco coverage reports for the debug build."

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }

    val fileFilter = mutableSetOf("**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*")
    val debugTree = fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))

    executionData.setFrom(fileTree(project.buildDir) {
        include("jacoco/testDebugUnitTest.exec", "outputs/code-coverage/connected/*coverage.ec")
    })
}
