package app.soulcramer.arn.remote

import app.soulcramer.arn.remote.model.user.UserModel
import app.soulcramer.arn.remote.model.user.graphql.GraphQLRequest
import app.soulcramer.arn.remote.model.user.graphql.UserListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Defines the abstract methods used for interacting with the NotifyMoe API
 */
interface NotifyMoeService {
    /**
     * @param userId
     */
    @GET("api/user/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): UserModel

    /**
     * @param nick
     */
    @GET("api/user/{userId}")
    fun getUserByNickname(@Path("nick") nick: String): ApiResponse<UserModel>

    /**
     * Request all users by using the graphql endpoint of Notify.moe
     * @param body don't override it
     */
    @POST("graphql")
    suspend fun getAllUsers(
        @Body predifinedQraphqlBody: GraphQLRequest = GraphQLRequest(
            "{\n  allUser{\nID\nNick\nRole\nRegistered\nAvatar{\nExtension\n}\nCover{\nExtension\n}\n}\n}\n"
        )
    ): UserListResponse

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
