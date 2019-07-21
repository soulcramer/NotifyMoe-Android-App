package app.soulcramer.arn.data.source

import androidx.paging.DataSource
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserDataStore
import app.soulcramer.arn.data.repository.UserRemote

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class UserRemoteDataStore(private val userRemote: UserRemote) :
    UserDataStore {

    override suspend fun saveUser(user: UserEntity) {
        throw UnsupportedOperationException()
    }

    override suspend fun saveUsers(users: List<UserEntity>) {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a [UserEntity] instances from the API
     */
    override suspend fun getUser(userId: String): UserEntity {
        return userRemote.getUser(userId)
    }

    override suspend fun searchUsers(nickname: String): DataSource.Factory<Int, UserEntity> {
        throw UnsupportedOperationException()
    }

    suspend fun searchAllUsers(nickname: String): List<UserEntity> {
        return userRemote.searchUsers(nickname)
    }
}