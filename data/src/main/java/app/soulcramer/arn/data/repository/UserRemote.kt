package app.soulcramer.arn.data.repository

import app.soulcramer.arn.data.model.UserEntity

/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface UserRemote {

    /**
     * Retrieve a user, from the cache
     */
    fun getUser(userId: String): UserEntity

    /**
     * Retrieve a user, from the cache
     */
    fun getUserByNickname(nick: String): UserEntity

}