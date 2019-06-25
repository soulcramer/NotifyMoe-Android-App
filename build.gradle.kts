import io.gitlab.arturbosch.detekt.detekt
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.fabric.io/public")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.android_gradle}")
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.navigation}")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}")
        classpath("com.google.gms:google-services:4.2.0")
        classpath("io.fabric.tools:gradle:1.29.0")
        classpath("com.google.firebase:firebase-plugins:2.0.0")
        classpath("org.jacoco:org.jacoco.core:0.8.4")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.0.0-RC15")
    id("org.jlleitschuh.gradle.ktlint").version(Versions.ktlintGradle)
    id("com.github.ben-manes.versions").version(Versions.benManes)
    `git-hooks`
}

allprojects {

    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.fabric.io/public")
    }

    configurations.all {
        resolutionStrategy {
            force("org.objenesis:objenesis:2.6")
        }
    }
}

subprojects {
    apply { plugin("org.jlleitschuh.gradle.ktlint") }

    ktlint {
        android.set(true)
        reporters.set(setOf(ReporterType.CHECKSTYLE))
        ignoreFailures.set(true)
        debug.set(false)
        verbose.set(false)
        outputToConsole.set(false)

        filter {
            exclude("**/generated/**", "**/*.kts")
            include("**/kotlin/**")
        }
    }
}

detekt {
    toolVersion = "1.0.0-RC12"
    input = files("$projectDir/app/src/main/java",
        "$projectDir/cache/src/main/java",
        "$projectDir/core/src/main/java",
        "$projectDir/data/src/main/java",
        "$projectDir/domain/src/main/java",
        "$projectDir/local/src/main/java",
        "$projectDir/remote/src/main/java"
    )
    config = files("$projectDir/default-detekt-config.yml")
    isIgnoreFailures = true

    reports {
        html {
            enabled = true
            destination = file("reports/detekt.html")
        }
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt.xml")
        }
    }
}

tasks {

    register<Delete>("clean") {
        description = "Delete the root project build folder"
        group = "cleanup"
        delete(rootProject.buildDir)
    }

    register("runChecksForDanger") {
        group = "Reporting"
        dependsOn(named("ktlintCheck"),
            "${Modules.domain}:lint",
            "${Modules.data}:lint",
            "${Modules.cache}:lint",
            "${Modules.remote}:lint",
            "${Modules.core}:lint",
            "${Modules.app}:lint"
        )

        val file = file("${project.rootDir}/build/reports/ktlint")
        if (!file.exists()) file.mkdirs()
        val lintFile = File("${project.rootDir}/build/reports/lint")
        if (!lintFile.exists()) lintFile.mkdirs()
    }
}
