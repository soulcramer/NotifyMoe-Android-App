package app.soulcramer.arn.data

import app.soulcramer.arn.database.AnimeDao
import app.soulcramer.arn.model.anime.Anime
import app.soulcramer.arn.remote.NotifyMoeService

/**
 * Repository that handles User objects.
 */
class AnimeRepository(
    private val animeDao: AnimeDao,
    private val service: NotifyMoeService
) {

    fun loadAnimeById(id: String): Anime? {
        var anime: Anime
        try {
            anime = animeDao.loadById(id)
        } catch (e: Exception) {
            anime = service.getAnimeById(id)
            animeDao.insertAnimes(anime)
        }
        return anime
    }
}
