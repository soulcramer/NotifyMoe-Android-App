plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("git-hooks-plugin") {
            id = "git-hooks"
            implementationClass = "GitHooksPlugin"
        }
    }
}

// Required since Gradle 4.10+.
repositories {
    jcenter()
}