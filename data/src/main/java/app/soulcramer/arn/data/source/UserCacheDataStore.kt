package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserCache
import app.soulcramer.arn.data.repository.UserDataStore

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class UserCacheDataStore(private val userCache: UserCache) : UserDataStore {

    override suspend fun searchUsers(nickname: String): List<UserEntity> {
        return userCache.searchUsers(nickname)
    }

    /**
     * Save a given [UserEntity] instance to the cache
     */
    override fun saveUser(user: UserEntity) {
        userCache.saveUser(user)
        userCache.setLastCacheTime(System.currentTimeMillis())
    }

    /**
     * Retrieve a [UserEntity] instance from the cache
     */
    override suspend fun getUser(userId: String): UserEntity {
        return userCache.getUser(userId)
    }
}