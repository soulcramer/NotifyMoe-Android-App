@file:Suppress("PublicApiImplicitType")

object Modules {
    const val app = ":app"

    const val core = ":core"
    const val remote = ":remote"

    const val cache = ":cache"

    const val data = ":data"
    const val domain = ":domain"
}

object Versions {

    object AndroidX {
        const val activity = "1.0.0-beta01"
        const val core = "1.2.0-alpha02"
        const val constraint_layout = "2.0.0-beta1"
        const val fragment = "1.1.0-beta01"
        const val emoji = "1.0.0"
        const val lifecycle = "2.2.0-alpha01"
        const val paging = "2.1.0"
        const val material = "1.1.0-alpha07"
        const val navigation = "2.1.0-alpha05"
        const val recyclerview = "1.1.0-alpha06"
        const val room = "2.1.0"
    }

    const val android_gradle = "3.5.0-beta05"
    const val benManes = "0.21.0"
    const val buildScan = "2.3"
    const val coroutines = "1.2.1"
    const val detekt = "1.0.0-RC15"
    const val epoxy = "3.6.0"
    const val fast_adapter = "3.3.1"
    const val glide = "4.9.0"
    const val gson = "2.8.5"
    const val koin = "2.0.1"
    const val kotlin = "1.3.41"
    const val ktlint = "0.33.0"
    const val ktlintGradle = "8.0.0"
    const val leakcanary = "2.0-alpha-2"
    const val moshi = "1.8.0"
    const val okhttp = "4.0.0-alpha02"
    const val photoview = "2.1.3"
    const val retrofit = "2.6.0"
    const val timberkt = "1.5.1"
    const val threetenabp = "1.2.0"
    const val threetenbp = "1.4.0"

    object Test {
        // Core library
        const val core = "1.2.0"

        // AndroidJUnitRunner and JUnit Rules
        const val runner = "1.2.0"
        const val rules = "1.1.0"

        // Assertions
        const val junitKtx = "1.1.1"
        const val truthKtx = "1.2.0"
        const val truth = "0.45"

        // Espresso dependencies
        const val espresso = "3.2.0"

        const val robolectric = "4.3"
        const val mockk = "1.9.3"
    }
}

object Libraries {

    object AndroidX {
        const val activity = "androidx.activity:activity-ktx:${Versions.AndroidX.activity}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraint_layout}"
        const val core = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val emoji = "androidx.emoji:emoji:${Versions.AndroidX.emoji}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        const val materialComponent = "com.google.android.material:material:${Versions.AndroidX.material}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"
        const val paging = "androidx.paging:paging-runtime-ktx:${Versions.AndroidX.paging}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerview}"
        const val room = "androidx.room:room-ktx:${Versions.AndroidX.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.AndroidX.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.AndroidX.room}"
    }

    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
    const val epoxyDataBinding = "com.airbnb.android:epoxy-databinding:${Versions.epoxy}"
    const val epoxyPaging = "com.airbnb.android:epoxy-paging:${Versions.epoxy}"

    const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // DI
    const val koin = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinAndroidXScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val fastAdapter = "com.mikepenz:fastadapter:${Versions.fast_adapter}"
    const val fastAdapterCommons = "com.mikepenz:fastadapter-commons:${Versions.fast_adapter}"
    const val fastAdapterExtensions = "com.mikepenz:fastadapter-extensions:${Versions.fast_adapter}"
    const val fastAdapterExpandable = "com.mikepenz:fastadapter-extensions-expandable:${Versions.fast_adapter}"

    // Leak Canary
    const val leackCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotation = "com.github.bumptech.glide:annotations:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val glideOkhttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    const val timberKt = "com.github.ajalt:timberkt:${Versions.timberkt}"

    object Test {
        // Core library
        const val core = "androidx.test:core:${Versions.Test.core}"
        const val fragment = "androidx.fragment:fragment-testing:${Versions.AndroidX.fragment}"

        // AndroidJUnitRunner and JUnit Rules
        const val runner = "androidx.test:runner:${Versions.Test.runner}"
        const val rules = "androidx.test:rules:${Versions.Test.rules}"
        const val room = "androidx.room:room-testing:${Versions.AndroidX.room}"

        // Assertions
        const val junitKtx = "androidx.test.ext:junit:${Versions.Test.junitKtx}"
        const val truthKtx = "androidx.test.ext:truth:${Versions.Test.truthKtx}"
        const val truth = "com.google.truth:truth:${Versions.Test.truth}"

        // Espresso dependencies
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Test.espresso}"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.Test.espresso}"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.Test.espresso}"
        const val espressoAccessibility = "androidx.test.espresso:espresso-accessibility:${Versions.Test.espresso}"
        const val espressoWeb = "androidx.test.espresso:espresso-web:${Versions.Test.espresso}"
        const val espressoIdlingConcurent = "androidx.test.espresso.idling:idling-concurrent:${Versions.Test.espresso}"

        // The following Espresso dependency can be either "implementation"
        // or "androidTestImplementation", depending on whether you want the
        // dependency to appear on your APK's compile classpath or the test APK
        // classpath.
        const val espressoIdlingRes = "androidx.test.espresso:espresso-idling-resource:${Versions.Test.espresso}"

        const val robolectric = "org.robolectric:robolectric:${Versions.Test.robolectric}"
        const val robolectricAnnotations = "org.robolectric:annotations:${Versions.Test.robolectric}"

        const val mockk = "io.mockk:mockk:${Versions.Test.mockk}"
        const val mockkInstrumented = "io.mockk:mockk-android:${Versions.Test.mockk}"

        const val koin = "org.koin:koin-test:${Versions.koin}"
    }
}

object BuildConfigs {
    const val compileSdk = 28
    const val minSdk = 26
    const val targetSdk = 28
}