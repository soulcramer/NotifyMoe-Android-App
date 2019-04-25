package app.soulcramer.arn.repository

import androidx.lifecycle.LiveData
import app.soulcramer.arn.api.NotifyMoeService
import app.soulcramer.arn.db.AnimeListDao
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.animelist.AnimeListItem

/**
 * Repository that handles User objects.
 */
class AnimeListRepository(
    private val animeListDao: AnimeListDao,
    private val service: NotifyMoeService
) {

    fun loadItemsByUserId(id: String): LiveData<Resource<List<AnimeListItem>>> {

        return object : NetworkBoundResource<List<AnimeListItem>, List<AnimeListItem>>() {
            override fun saveCallResult(item: List<AnimeListItem>) {
                animeListDao.insert(id, item)
            }

            override fun shouldFetch(data: List<AnimeListItem>?) = data?.isEmpty() ?: true

            override fun loadFromDb(): LiveData<List<AnimeListItem>> {
                return animeListDao.findByUserId(id)
            }

            override fun createCall() = service.getAnimeListItemsByUserId(id)

        }.asLiveData()
    }
}
