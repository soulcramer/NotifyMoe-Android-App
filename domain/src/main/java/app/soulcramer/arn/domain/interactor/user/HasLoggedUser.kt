package app.soulcramer.arn.domain.interactor.user

import app.soulcramer.arn.domain.interactor.UseCase
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.prefs.PreferenceStorage

/**
 * Use case used for checking if there is a [User] in the local session from the [PreferenceStorage]
 */
class HasLoggedUser(private val preferenceStorage: PreferenceStorage) : UseCase<Unit, Boolean>() {
    override suspend fun execute(parameters: Unit): Boolean = preferenceStorage.currentUserId != null
}