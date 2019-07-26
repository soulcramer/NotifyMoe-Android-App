package app.soulcramer.arn.domain.interactor.user

import app.soulcramer.arn.domain.interactor.UseCase
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.prefs.PreferenceStorage

/**
 * Use case used for logging out the [User] from the local session in the [PreferenceStorage]
 */
class LogOutUser(private val preferenceStorage: PreferenceStorage) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        preferenceStorage.currentUserId = null
    }
}