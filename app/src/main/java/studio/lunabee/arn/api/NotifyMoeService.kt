package studio.lunabee.arn.api

import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import studio.lunabee.arn.vo.NickToUser
import studio.lunabee.arn.vo.User

/**
 * REST API access points
 */
interface NotifyMoeService {
    /**
     * @param userId
     */
    @GET("/api/user/{userId}")
    fun getUserById(@Path("userId") userId: String): LiveData<ApiResponse<User>>

    /**
     * @param userId
     */
    @GET("/api/user/{userId}")
    fun getUserByIdK(@Path("userId") userId: String): Deferred<Response<User>>

    /**
     * @param userNickname
     */
    @GET("/api/nicktouser/{userNickname}")
    fun getUserIdByNick(@Path("userNickname") userNickname: String): Deferred<Response<NickToUser>>
}