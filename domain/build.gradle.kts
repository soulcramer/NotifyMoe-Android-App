import com.android.build.gradle.internal.dsl.TestOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    id("jacoco")
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
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = true
            isTestCoverageEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        execution = "ANDROID_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            setIncludeAndroidResources(true)
        })
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
}

dependencies {
    implementation(project(Modules.core))

    implementation(Libraries.kotlinStandardLibrary)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)

    implementation(Libraries.AndroidX.livedata)
    implementation(Libraries.AndroidX.paging)

    implementation(Libraries.timberKt)

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
