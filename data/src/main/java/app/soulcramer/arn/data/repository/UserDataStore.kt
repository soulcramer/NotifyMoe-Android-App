package app.soulcramer.arn.data.repository

import app.soulcramer.arn.data.model.UserEntity

/**
 * Interface defining methods for the data operations related to Users.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface UserDataStore {

    fun saveUser(user: UserEntity)

    suspend fun getUser(userId: String): UserEntity

    suspend fun searchUsers(nickname: String): List<UserEntity>
}