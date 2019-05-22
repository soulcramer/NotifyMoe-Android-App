package app.soulcramer.arn.remote

import androidx.lifecycle.LiveData
import app.soulcramer.arn.model.anime.Anime
import app.soulcramer.arn.model.animelist.AnimeListItem
import app.soulcramer.arn.model.user.NickToUser
import app.soulcramer.arn.model.user.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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
    fun getAnimeById(@Path("animeId") animeId: String): Anime

    /**
     * @param animeId
     */
    @GET("api/anime/{animeId}")
    fun getAnimeByIdDeffered(@Path("animeId") animeId: String): Deferred<Response<Anime>>
}
