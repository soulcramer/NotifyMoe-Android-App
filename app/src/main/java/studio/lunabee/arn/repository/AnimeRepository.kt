package studio.lunabee.arn.repository

import android.arch.lifecycle.LiveData
import studio.lunabee.arn.AppCoroutineDispatchers
import studio.lunabee.arn.api.NotifyMoeService
import studio.lunabee.arn.db.AnimeDao
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.anime.Anime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@Singleton
class AnimeRepository @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val animeDao: AnimeDao,
    private val service: NotifyMoeService
) {

    fun loadAnimeById(id: String): LiveData<Resource<Anime>> {
        return object : NetworkBoundResource<Anime, Anime>(dispatchers) {
            override fun saveCallResult(item: Anime) {
                animeDao.insert(item)
            }

            override fun shouldFetch(data: Anime?) = data == null

            override fun loadFromDb() = animeDao.findById(id)

            override fun createCall() = service.getAnimeById(id)
        }.asLiveData()
    }
}
