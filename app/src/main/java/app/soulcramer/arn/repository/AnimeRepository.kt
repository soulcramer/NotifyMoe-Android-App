package app.soulcramer.arn.repository

import androidx.lifecycle.LiveData
import app.soulcramer.arn.api.NotifyMoeService
import app.soulcramer.arn.db.AnimeDao
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.anime.Anime

/**
 * Repository that handles User objects.
 */
class AnimeRepository(
    private val animeDao: AnimeDao,
    private val service: NotifyMoeService
) {

    fun loadAnimeById(id: String): LiveData<Resource<Anime>> {
        return object : NetworkBoundResource<Anime, Anime>() {
            override fun saveCallResult(item: Anime) {
                animeDao.insertAnimes(item)
            }

            override fun shouldFetch(data: Anime?) = data == null

            override fun loadFromDb() = animeDao.loadById(id)

            override fun createCall() = service.getAnimeById(id)
        }.asLiveData()
    }
}
