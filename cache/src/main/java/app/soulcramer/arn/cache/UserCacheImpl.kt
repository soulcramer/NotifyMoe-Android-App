package app.soulcramer.arn.cache

import app.soulcramer.arn.cache.mapper.UserEntityMapper
import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserCache
import java.util.concurrent.TimeUnit

/**
 * Cached implementation for retrieving and saving User instances. This class implements the
 * [UserCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserCacheImpl(
    val database: NotifyMoeDatabase,
    private val entityMapper: UserEntityMapper,
    private val preferencesHelper: PreferencesHelper
) : UserCache {

    private val EXPIRATION_TIME = TimeUnit.HOURS.toMillis(3)

    private val userDao = database.userDao()

    /**
     * Save the given list of [UserEntity] instances to the database.
     */
    override suspend fun saveUser(user: UserEntity) {
        return saveUser(entityMapper.mapToCached(user))
    }

    /**
     * Retrieve a list of [UserEntity] instances from the database.
     */
    override suspend fun getUser(userId: String): UserEntity {
        val cachedUser = userDao.loadById(userId)
        return entityMapper.mapFromCached(cachedUser)
    }

    /**
     * Retrieve a list of [UserEntity] instances from the database.
     */
    override suspend fun getUsers(): List<UserEntity> {
        val cachedUsers = userDao.getAll()
        return cachedUsers.map(entityMapper::mapFromCached)
    }

    override suspend fun searchUsers(nickname: String): List<UserEntity> {
        val cachedUsers = userDao.searchByNickname(nickname)
        return cachedUsers.map(entityMapper::mapFromCached)
    }

    /**
     * Helper method for saving a [CachedUser] instance to the database.
     */
    private suspend fun saveUser(cachedUser: CachedUser) {
        userDao.insertUsers(cachedUser)
    }

    /**
     * Checked whether there are instances of [CachedUser] stored in the cache
     */
    override suspend fun isCached(): Boolean {
        return userDao.allUserCount() > 0
    }

    /**
     * Set a point in time at when the cache was last updated
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }
}