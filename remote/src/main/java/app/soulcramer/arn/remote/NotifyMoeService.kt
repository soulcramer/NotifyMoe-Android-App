package app.soulcramer.arn.remote

import app.soulcramer.arn.remote.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Defines the abstract methods used for interacting with the NotifyMoe API
 */
interface NotifyMoeService {
    /**
     * @param userId
     */
    @GET("api/user/{userId}")
    fun getUserById(@Path("userId") userId: String): Response<UserModel>

    /**
     * @param nick
     */
    @GET("api/user/{userId}")
    fun getUserByNickname(@Path("nick") nick: String): ApiResponse<UserModel>

    //    /**
    //     * @param userId
    //     */
    //    @GET("api/user/{userId}")
    //    fun getUserByIdK(@Path("userId") userId: String): Deferred<Response<User>>

    //    /**
    //     * @param userNickname
    //     */
    //    @GET("api/nicktouser/{userNickname}")
    //    fun getUserIdByNick(@Path("userNickname") userNickname: String): Deferred<Response<NickToUser>>
}
