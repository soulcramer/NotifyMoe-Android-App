object Modules {
    val app = ":app"
    val model = ":model"

    val core = ":core"
    val service = ":service"

    val database = ":database"

    val repository = ":repository"
}

object Versions {
    val kotlin = "1.3.31"
    val android_gradle = "3.5.0-beta01"

    val activity = "1.0.0-alpha08"
    val appcompat = "1.1.0-aplha04"
    val arch_core_testing = "2.0.0"
    val browser = "1.0.0"
    val cardview = "1.0.0"
    val core = "1.1.0-alpha05"
    val constraint_layout = "2.0.0-beta1"
    val core_ktx = "1.2.0-alpha01"
    val databinding = "1.1.0-alpha05"
    val fragment = "1.1.0-alpha09"
    val emoji = "1.0.0"
    val lifecycle = "2.2.0-alpha01"
    val multidex = "2.0.1"
    val navigation = "2.1.0-alpha04"
    val paging = "2.1.0"
    val recyclerview = "1.1.0-alpha05"
    val room = "2.1.0-beta01"
    val test_core = "1.0.0"
    val test_junit = "1.0.0"
    val test_runner = "1.1.0"
    val test_rules = "1.1.0"

    val anko = "0.10.4"
    val bolts = "1.4.0"
    val coroutines = "1.2.1"
    val dagger = "2.22.1"
    val dokka = "0.9.16"
    val fast_adapter = "3.3.1"
    val glide = "4.9.0"
    val gson = "2.8.5"
    val jfrog = "4.7.3"
    val koin = "2.0.0-rc-3"
    val ktlint = "0.22.0"
    val leakcanary = "2.0-alpha-1"
    val material = "1.1.0-alpha06"
    val monarchy = "0.5.1"
    val okhttp = "3.14.1"
    val parse = "1.17.3"
    val parse_livequery = "1.0.6"
    val photoview = "2.1.3"
    val retrofit = "2.5.0"
    val timberkt = "1.5.1"
    val threetenabp = "1.2.0"
    val threetenbp = "1.4.0"

}

object Libraries {
    val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    val threetenabp = "com.jakewharton.threetenabp:threetenabp:${Versions.threetenabp}"
    val threetenbp = "org.threeten:threetenbp:${Versions.threetenbp}:no-tzdb"

    // DI

    val koin = "org.koin:koin-core:${Versions.koin}"
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinAndroidXScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    val fastAdapter = "com.mikepenz:fastadapter:${Versions.fast_adapter}"
    val fastAdapterCommons = "com.mikepenz:fastadapter-commons:${Versions.fast_adapter}"
    val fastAdapterExtensions = "com.mikepenz:fastadapter-extensions:${Versions.fast_adapter}"
    val fastAdapterExpandable = "com.mikepenz:fastadapter-extensions-expandable:${Versions.fast_adapter}"

    // Leak Canary
    val leackCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"

    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideAnnotation = "com.github.bumptech.glide:annotations:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    val glideOkhttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    val timberKt = "com.github.ajalt:timberkt:${Versions.timberkt}"

    val gson = "com.google.code.gson:gson:${Versions.gson}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

}

object LibrariesAndroidX {
    val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val materialComponent = "com.google.android.material:material:${Versions.material}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    val core = "androidx.core:core-ktx:${Versions.core_ktx}"
    val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val emoji = "androidx.emoji:emoji:${Versions.emoji}"
    val room = "androidx.room:room-ktx:${Versions.room}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
}

object BuildConfigs {
    val compileSdk = 28
    val minSdk = 23
    val targetSdk = 28
}