package app.soulcramer.arn.data.repository

import app.soulcramer.arn.data.model.UserEntity

/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserCache {

    //    /**
    //     * Clear all Users from the cache
    //     */
    //    fun clearUsers(): Completable

    /**
     * Save a given user to the cache
     */
    fun saveUser(user: UserEntity)

    /**
     * Retrieve a User, from the cache
     */
    suspend fun getUser(userId: String): UserEntity

    /**
     * Retrieve a list of Users with similar nickname, from the cache
     */
    suspend fun searchUsers(nickname: String): List<UserEntity>

    /**
     * Checks if an element (User) exists in the cache.
     * @param userId The id used to look for inside the cache.
     *
     * @return true if the element is cached, otherwise false.
     */
    suspend fun isCached(): Boolean

    /**
     * Checks if an element (User) exists in the cache.
     * @param userId The id used to look for inside the cache.
     * *
     * @return true if the element is cached, otherwise false.
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Checks if the cache is expired.
     * @return true, the cache is expired, otherwise false.
     */
    fun isExpired(): Boolean
}