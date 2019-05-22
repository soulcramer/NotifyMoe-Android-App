package app.soulcramer.arn.data

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository

/**
 * Provides an implementation of the [BufferooRepository] interface for communicating to and from
 * data sources
 */
class UserDataRepository(
    private val factory: UserDataStoreFactory,
    private val userMapper: UserMapper) : UserRepository {

    override fun getUser(userId: String): User {
        val dataStore = factory.retrieveDataStore().getUser(userId)
        return dataStore.getUser(userId).let {
            if (dataStore is UserRemoteDataStore) {
                saveUserEntity(it)
            }
            it
        }.run { user ->
            userMapper.mapFromEntity(user)
        }
    }

    private fun saveUserEntity(user: UserEntity): Completable {
        return factory.retrieveCacheDataStore().saveUSer(user)
    }

}