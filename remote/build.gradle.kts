import com.android.build.gradle.internal.dsl.TestOptions

plugins {
    id("com.android.library")
    id("jacoco")
    kotlin("android")
    kotlin("kapt")
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
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        execution = "ANDROID_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            isIncludeAndroidResources = true
        })
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(Modules.data))
    implementation(project(Modules.core))

    implementation(Libraries.koin)

    implementation(Libraries.moshi)
    kapt(Libraries.moshiCodeGen)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitMoshi)
    implementation(Libraries.okhttpLogging)

    implementation(Libraries.AndroidX.livedata)

    testImplementation(Libraries.Test.core)
    testImplementation(Libraries.Test.runner)
    testImplementation(Libraries.Test.truth)
    testImplementation(Libraries.Test.truthKtx)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Test.mockk)
}

jacoco {
    toolVersion = Versions.Test.jacoco
}

tasks.withType(Test::class.java) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {

    dependsOn("testDebugUnitTest", "createDebugCoverageReport")
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
