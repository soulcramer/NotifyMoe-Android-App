package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.repository.UserCache
import app.soulcramer.arn.data.repository.UserDataStore

/**
 * Create an instance of a UserDataStore
 */
open class UserDataStoreFactory(
    private val userCache: UserCache,
    private val userCacheDataStore: UserCacheDataStore,
    private val userRemoteDataStore: UserRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(): UserDataStore {
        if (userCache.isCached() && !userCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveCacheDataStore(): UserDataStore {
        return userCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): UserDataStore {
        return userRemoteDataStore
    }

}