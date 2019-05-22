package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserDataStore
import app.soulcramer.arn.data.repository.UserRemote

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class UserRemoteDataStore(private val userRemote: UserRemote) :
    UserDataStore {

    override fun saveUser(user: UserEntity) {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a [UserEntity] instances from the API
     */
    override fun getUser(userId: String): UserEntity {
        return userRemote.getUser(userId)
    }

}