import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import java.util.Locale

/**
 * Adapted from https://proandroiddev.com/ooga-chaka-git-hooks-to-enforce-code-quality-11ce8d0d23cb
 */
class GitHooksPlugin : Plugin<Project> {

    fun isLinuxOrMacOs(): Boolean {
        val osName = System.getProperty("os.name").toLowerCase(Locale.ROOT)
        return osName.contains("linux") || osName.contains("mac os") || osName.contains("macos")
    }

    override fun apply(project: Project): Unit = project.run {

        tasks {

            register("copyGitHooks", Copy::class.java) {
                description = "Copies the git hooks from scripts/git-hooks to the .git folder."
                from("$rootDir/scripts/git-hooks/") {
                    include("**/*.sh")
                    rename("(.*).sh", "$1")
                }
                into("$rootDir/.git/hooks")
                onlyIf { isLinuxOrMacOs() }
            }

            register("installGitHooks", Exec::class.java) {
                description = "Installs the git hooks from scripts/git-hooks."
                group = "git hooks"
                workingDir(rootDir)
                commandLine("chmod")
                args("-R", "+x", ".git/hooks/")
                dependsOn(tasks.named("copyGitHooks"))
                onlyIf { isLinuxOrMacOs() }
                doLast {
                    logger.info("Git hooks installed successfully.")
                }
            }

            afterEvaluate {
                // We install the hook at the first occasion
                tasks["clean"].dependsOn(tasks.named("installGitHooks"))
            }
        }
    }
}