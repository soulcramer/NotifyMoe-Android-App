package app.soulcramer.arn.data

import app.soulcramer.arn.data.mapper.UserMapper
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.source.UserDataStoreFactory
import app.soulcramer.arn.data.source.UserRemoteDataStore
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
        val dataStore = factory.retrieveDataStore()
        return dataStore.getUser(userId).let {
            if (dataStore is UserRemoteDataStore) {
                saveUserEntity(it)
            }
            it
        }.let { user ->
            userMapper.mapFromEntity(user)
        }
    }

    private fun saveUserEntity(user: UserEntity) {
        return factory.retrieveCacheDataStore().saveUser(user)
    }

}