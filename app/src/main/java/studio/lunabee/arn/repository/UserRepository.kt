package studio.lunabee.arn.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import studio.lunabee.arn.AppCoroutineDispatchers
import studio.lunabee.arn.api.ApiErrorResponse
import studio.lunabee.arn.api.ApiResponse
import studio.lunabee.arn.api.NotifyMoeService
import studio.lunabee.arn.db.UserDao
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.user.NickToUser
import studio.lunabee.arn.vo.user.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@Singleton
class UserRepository @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val userDao: UserDao,
    private val service: NotifyMoeService
) {

    fun loadUserById(id: String): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, User>(dispatchers) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: List<User>?) = data == null || data.isEmpty()

            override fun loadFromDb() = userDao.findById(id)

            override fun createCall() = service.getUserById(id)
        }.asLiveData()
    }

    fun loadUserByNickName(nick: String): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, User>(dispatchers) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: List<User>?) = data == null || data.isEmpty()

            override fun loadFromDb() = userDao.findByNick(nick)

            override fun createCall(): LiveData<ApiResponse<User>> = loadUserByNick(nick)
        }.asLiveData()
    }

    private fun loadUserByNick(
        nick: String
    ): MutableLiveData<ApiResponse<User>> {
        val dispatchResult = MutableLiveData<ApiResponse<User>>()
        launch {
            try {
                val nickResponse = service.getUserIdByNick(nick).await()
                if (nickResponse.isSuccessful) {
                    loadUserById(nickResponse, dispatchResult)
                } else {
                    dispatchResult.postValue(ApiErrorResponse(nickResponse.message()))
                }
            } catch (exception: Exception) {
                dispatchResult.postValue(ApiResponse.create(exception))
            }
        }
        return dispatchResult
    }

    private suspend fun loadUserById(
        nickResponse: Response<NickToUser>,
        dispatchResult: MutableLiveData<ApiResponse<User>>
    ) {
        val idResponse = service.getUserByIdK(nickResponse.body()?.userId!!).await()
        if (idResponse.isSuccessful) {
            dispatchResult.postValue(ApiResponse.create(idResponse))
        } else {
            dispatchResult.postValue(ApiResponse.create(idResponse))
        }
    }
}