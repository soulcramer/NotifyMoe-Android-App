package app.soulcramer.arn.data

import app.soulcramer.arn.data.mapper.UserMapper
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.source.UserDataStoreFactory
import app.soulcramer.arn.data.source.UserRemoteDataStore
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class UserDataRepository(
    private val factory: UserDataStoreFactory,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun searchUser(nickname: String): List<User> {
        val dataStore = factory.retrieveDataStore()
        return dataStore.searchUsers(nickname).let { users ->
            if (dataStore is UserRemoteDataStore) {
                supervisorScope {
                    launch {
                        saveUserEntities(users)
                    }
                }
            }
            users
        }.map(userMapper::mapFromEntity)
    }

    override suspend fun getUser(userId: String): User {
        val dataStore = factory.retrieveDataStore()
        return dataStore.getUser(userId).let {
            if (dataStore is UserRemoteDataStore) {
                supervisorScope {
                    launch {
                        saveUserEntity(it)
                    }
                }
            }
            it
        }.let { user ->
            userMapper.mapFromEntity(user)
        }
    }

    private suspend fun saveUserEntity(user: UserEntity) {
        return factory.retrieveCacheDataStore().saveUser(user)
    }

    private suspend fun saveUserEntities(users: List<UserEntity>) {
        return factory.retrieveCacheDataStore().saveUsers(users)
    }
}