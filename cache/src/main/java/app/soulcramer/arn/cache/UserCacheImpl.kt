package app.soulcramer.arn.cache

import app.soulcramer.arn.cache.mapper.UserEntityMapper
import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.data.mapper.UserMapper
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserCache

/**
 * Cached implementation for retrieving and saving User instances. This class implements the
 * [UserCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserCacheImpl(private val database: NotifyMoeDatabase,
    private val entityMapper: UserEntityMapper,
    private val mapper: UserMapper,
    private val preferencesHelper: PreferencesHelper) :
    UserCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    private val userDao = database.userDao()

    /**
     * Save the given list of [UserEntity] instances to the database.
     */
    override fun saveUser(user: UserEntity) {
        return saveUser(entityMapper.mapToCached(user))
    }

    /**
     * Retrieve a list of [UserEntity] instances from the database.
     */
    override fun getUser(userId: String): UserEntity {
        val cachedUser = userDao.loadById(userId)
        return entityMapper.mapFromCached(cachedUser)
    }

    /**
     * Helper method for saving a [CachedUser] instance to the database.
     */
    private fun saveUser(cachedUser: CachedUser) {
        userDao.insertUsers(cachedUser)
    }

    /**
     * Checked whether there are instances of [CachedUser] stored in the cache
     */
    override fun isCached(): Boolean {
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