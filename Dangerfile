# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

message "Thanks @#{github.pr_author} 👍👍"

warn("You should provide a summary in the Pull Request description so that the reviewer has more context on this Pull Request 🤔") if github.pr_body.length < 50

#
# Notify of the release APK size.
#
#apk_size = (File.size('app/build/outputs/apk/release/app-release-unsigned.apk').to_f / 2**20).round(2)
#message "Release APK size: #{apk_size} MB"


# Ignore inline messages which lay outside a diff's range of PR
github.dismiss_out_of_range_messages

#
# Notify of outdated dependencies
#
update_count = File.readlines("build/dependencyUpdates/report.txt").select { |line| line =~ /->/ }.count
if update_count > 10
  # More than 10 libraries to update is cumbersome in a comment, so summarize
  warn "There are #{update_count} dependencies with new milestone versions."
elsif update_count > 0
  file = File.open("build/dependencyUpdates/report.txt", "rb").read
  heading = "The following dependencies have later milestone versions:"
  warn file.slice(file.index(heading)..-1)
end

warn("This PR is quite a big one! Maybe try splitting this into separate tasks next time 🙂") if git.lines_of_code > 600

# AndroidLint
android_lint.report_file = "/bitrise/src/build/reports/lint/lint-report.xml"
android_lint.skip_gradle_task = true
android_lint.severity = "Error"
android_lint.lint(inline_mode: true)