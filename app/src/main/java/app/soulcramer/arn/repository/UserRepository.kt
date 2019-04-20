package app.soulcramer.arn.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.soulcramer.arn.api.ApiErrorResponse
import app.soulcramer.arn.api.ApiResponse
import app.soulcramer.arn.api.NotifyMoeService
import app.soulcramer.arn.db.UserDao
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.user.NickToUser
import app.soulcramer.arn.vo.user.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Repository that handles User objects.
 */
class UserRepository(
    private val userDao: UserDao,
    private val service: NotifyMoeService
) {

    fun loadUserById(id: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>() {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findById(id)

            override fun createCall() = service.getUserById(id)
        }.asLiveData()
    }

    fun loadUserByNickName(nick: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>() {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findByNick(nick)

            override fun createCall(): LiveData<ApiResponse<User>> = loadUserByNick(nick)
        }.asLiveData()
    }

    private fun loadUserByNick(
        nick: String
    ): MutableLiveData<ApiResponse<User>> {
        val dispatchResult = MutableLiveData<ApiResponse<User>>()
        GlobalScope.launch {
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
