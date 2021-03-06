import com.android.build.gradle.internal.dsl.TestOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
            isShrinkResources = false
            isMinifyEnabled = false
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
        animationsDisabled = true

        unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {
            isIncludeAndroidResources = true
        })
    }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
    kotlinOptions.jvmTarget = "1.8"
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Kotlin JDK
    implementation(Libraries.kotlinStandardLibrary)
    implementation(Libraries.kotlinCoroutines)
    implementation(Libraries.kotlinCoroutinesAndroid)

    implementation(project(Modules.core))
    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.cache))
    implementation(project(Modules.remote))

    // AndroidX
    implementation(Libraries.AndroidX.activity)
    implementation(Libraries.AndroidX.emoji)
    implementation(Libraries.AndroidX.constraintLayout)
    implementation(Libraries.AndroidX.core)
    implementation(Libraries.AndroidX.fragment)
    implementation(Libraries.AndroidX.lifecycle)
    implementation(Libraries.AndroidX.lifecycleRuntime)
    implementation(Libraries.AndroidX.materialComponent)
    implementation(Libraries.AndroidX.navigationFragment)
    implementation(Libraries.AndroidX.navigationUi)
    implementation(Libraries.AndroidX.paging)
    implementation(Libraries.AndroidX.recyclerView)

    // DI
    implementation(Libraries.koinAndroidXScope)
    implementation(Libraries.koinViewModel)

    implementation(Libraries.epoxy)
    implementation(Libraries.epoxyDataBinding)
    implementation(Libraries.epoxyPaging)
    kapt(Libraries.epoxyProcessor)

    // Leak Canary
    debugImplementation(Libraries.leackCanary)

    implementation(Libraries.glide)
    implementation(Libraries.glideAnnotation)
    kapt(Libraries.glideCompiler)

    implementation(Libraries.glideOkhttp)
    implementation(Libraries.timberKt)

    // Test
    testImplementation(Libraries.Test.core)
    testImplementation(Libraries.Test.runner)
    testImplementation(Libraries.Test.truth)
    testImplementation(Libraries.Test.truthKtx)
    testImplementation(Libraries.Test.robolectric)
    testImplementation(Libraries.Test.mockk)
    testImplementation(Libraries.Test.room)

    androidTestImplementation(Libraries.Test.core)
    debugImplementation(Libraries.Test.core)
    androidTestImplementation(Libraries.Test.runner)
    androidTestImplementation(Libraries.Test.truth)
    androidTestImplementation(Libraries.Test.truthKtx)
    androidTestImplementation(Libraries.Test.junitKtx)
    androidTestImplementation(Libraries.Test.koin)
    androidTestImplementation(Libraries.Test.mockkInstrumented)
    androidTestImplementation(Libraries.Test.espressoCore)
    androidTestImplementation(Libraries.Test.robolectricAnnotations)
    debugImplementation(Libraries.Test.fragment)
    androidTestImplementation(Libraries.Test.fragment)
}

fun <T> propOrDef(propertyName: String, defaultValue: T): T {
    return if (hasProperty(propertyName)) property(propertyName) as T else defaultValue
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
