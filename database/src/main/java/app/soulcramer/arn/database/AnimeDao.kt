package app.soulcramer.arn.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.soulcramer.arn.model.anime.Anime

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimes(vararg animes: Anime)

    @Update
    fun updateAnimes(vararg animes: Anime)

    @Delete
    fun deleteAnimes(vararg animes: Anime)

    @Query("SELECT * FROM animes WHERE id = :animeId")
    fun loadById(animeId: String): Anime
}
