buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.android_gradle}")
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}")
        classpath("com.google.gms:google-services:4.2.0")
        classpath("io.fabric.tools:gradle:1.28.0")
        classpath("com.google.firebase:firebase-plugins:2.0.0")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.0.0-RC12")
    id("com.diffplug.gradle.spotless").version("3.16.0")
    id("com.github.ben-manes.versions").version("0.21.0")

}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.fabric.io/public")
    }
}
//
//spotless {
//    kotlin {
//        target "**/*.kt"
//        ktlint(Versions.ktlint)
//    }
//}
//
//subprojects {
//    apply plugin: 'com.diffplug.gradle.spotless'
//
//
//    detekt {
//        toolVersion = "1.0.0-RC12"
//        input = files("$projectDir/app/src/main/java")
//        config = files("$projectDir/default-detekt-config.yml")
//        filters = ".*test.*,.*/resources/.*,.*/tmp/.*"
//        reports {
//            html {
//                enabled = true
//                destination = file("reports/detekt.html")
//            }
//        }
//    }
//}
