package app.soulcramer.arn.domain.repository

import androidx.paging.DataSource
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.User

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface UserRepository {

    suspend fun getUser(userId: String): User

    suspend fun searchUser(nickname: String, forceRefresh: Boolean): Result<DataSource.Factory<Int, User>>
}