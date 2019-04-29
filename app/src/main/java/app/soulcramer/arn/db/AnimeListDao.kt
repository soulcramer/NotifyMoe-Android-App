package app.soulcramer.arn.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.soulcramer.arn.vo.animelist.AnimeListItem

@Dao
interface AnimeListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimeListItems(items: List<AnimeListItem>)

    @Update
    fun updateAnimeListItems(items: List<AnimeListItem>)

    @Delete
    fun deleteAnimeListItems(items: List<AnimeListItem>)

    @Query("SELECT * FROM anime_list_items WHERE anime_id = :animeId & user_id = :userId")
    fun loadById(animeId: String, userId: String): LiveData<List<AnimeListItem>>

    @Query("SELECT * FROM anime_list_items WHERE anime_id = :animeId ")
    fun loadByAnimeId(animeId: String): LiveData<List<AnimeListItem>>

    @Query("SELECT anime_list_items.user_id AS userId, anime_list_items.anime_id AS animeId, " +
        "anime_list_items.status AS status, anime_list_items.episodes AS episodes, animes.canonical as animeName " +
        "FROM anime_list_items, animes WHERE anime_list_items.user_id = :userId " +
        "AND animes.id = anime_list_items.anime_id")
    fun loadByUserId(userId: String): LiveData<List<MappedAnimeItem>>

}
