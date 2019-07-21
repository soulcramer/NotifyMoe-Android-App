package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.prefs.PreferenceStorage

/**
 * Use case used for logging the [User] in the local session in the [PreferenceStorage]
 */
class LogInUser(private val preferenceStorage: PreferenceStorage) : UseCase<String, Unit>() {
    override suspend fun execute(parameters: String) {
        require(parameters.isNotBlank()) { "The user ID ”$parameters“ should not be blank to log the user" }
        preferenceStorage.currentUserId = parameters
    }
}