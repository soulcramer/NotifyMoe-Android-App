package studio.lunabee.arn.repository

import android.arch.lifecycle.LiveData
import studio.lunabee.arn.AppCoroutineDispatchers
import studio.lunabee.arn.api.NotifyMoeService
import studio.lunabee.arn.db.AnimeListDao
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.animelist.AnimeListItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@Singleton
class AnimeListRepository @Inject constructor(private val dispatchers: AppCoroutineDispatchers,
    private val animeListDao: AnimeListDao, private val service: NotifyMoeService) {

    fun loadAnimeListItemsByUserId(id: String): LiveData<Resource<List<AnimeListItem>>> {
        return object : NetworkBoundResource<List<AnimeListItem>, List<AnimeListItem>>(dispatchers) {
            override fun saveCallResult(items: List<AnimeListItem>) {
                animeListDao.insert(items)
            }

            override fun shouldFetch(data: List<AnimeListItem>?) = data == null || data.isEmpty()

            override fun loadFromDb() = animeListDao.findByUserId(id)

            override fun createCall() = service.getAnimeListItemsByUserId(id)
        }.asLiveData()
    }
}