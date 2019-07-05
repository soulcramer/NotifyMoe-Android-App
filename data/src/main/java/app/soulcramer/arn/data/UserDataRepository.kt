package app.soulcramer.arn.data

import android.accounts.NetworkErrorException
import androidx.paging.DataSource
import app.soulcramer.arn.data.mapper.UserMapper
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.source.UserDataStoreFactory
import app.soulcramer.arn.data.source.UserRemoteDataStore
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository
import kotlinx.coroutines.CancellationException
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

    override suspend fun searchUser(nickname: String, forceRefresh: Boolean): Result<DataSource.Factory<Int, User>> {
        val dataStore = factory.retrieveDataStore(forceRefresh)
        if (dataStore is UserRemoteDataStore) {
            try {
                val users = dataStore.searchAllUsers(nickname)
                saveUserEntities(users)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Result.Failure(NetworkErrorException(e), getSearchedUsersDataSource(nickname))
            }
        }
        return Result.Success(getSearchedUsersDataSource(nickname))
    }

    private suspend fun getSearchedUsersDataSource(nickname: String): DataSource.Factory<Int, User> {
        return factory.retrieveCacheDataStore()
            .searchUsers(nickname)
            .map(userMapper::mapFromEntity)
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