package studio.lunabee.arn.api

import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import studio.lunabee.arn.vo.anime.Anime
import studio.lunabee.arn.vo.animelist.AnimeListItem
import studio.lunabee.arn.vo.user.NickToUser
import studio.lunabee.arn.vo.user.User

/**
 * REST API access points
 */
interface NotifyMoeService {
    /**
     * @param userId
     */
    @GET("api/user/{userId}")
    fun getUserById(@Path("userId") userId: String): LiveData<ApiResponse<User>>

    /**
     * @param userId
     */
    @GET("api/user/{userId}")
    fun getUserByIdK(@Path("userId") userId: String): Deferred<Response<User>>

    /**
     * @param userNickname
     */
    @GET("api/nicktouser/{userNickname}")
    fun getUserIdByNick(@Path("userNickname") userNickname: String): Deferred<Response<NickToUser>>

    /**
     * @param userId
     */
    @GET("api/animelist/{userId}")
    fun getAnimeListItemsByUserId(@Path("userId") userId: String): LiveData<ApiResponse<List<AnimeListItem>>>

    /**
     * @param animeId
     */
    @GET("api/anime/{animeId}")
    fun getAnimeById(@Path("animeId") animeId: String): LiveData<ApiResponse<Anime>>
}
