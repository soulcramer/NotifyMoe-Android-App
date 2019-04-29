package app.soulcramer.arn.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.soulcramer.arn.api.ApiResponse
import app.soulcramer.arn.api.ApiSuccessResponse
import app.soulcramer.arn.api.NotifyMoeService
import app.soulcramer.arn.db.AnimeDao
import app.soulcramer.arn.db.AnimeListDao
import app.soulcramer.arn.db.MappedAnimeItem
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.animelist.AnimeListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Repository that handles User objects.
 */
class AnimeListRepository(
    private val animeListDao: AnimeListDao,
    private val animeDao: AnimeDao,
    private val service: NotifyMoeService
) {

    fun loadItemsByUserId(id: String): LiveData<Resource<List<MappedAnimeItem>>> {

        return object : NetworkBoundResource<List<MappedAnimeItem>, List<AnimeListItem>>() {
            override fun saveCallResult(item: List<AnimeListItem>) {
                animeListDao.insertAnimeListItems(item)
            }

            override fun shouldFetch(data: List<MappedAnimeItem>?) = data?.isEmpty() ?: true

            override fun loadFromDb(): LiveData<List<MappedAnimeItem>> {
                return animeListDao.loadByUserId(id)
            }

            override fun createCall(): LiveData<ApiResponse<List<AnimeListItem>>> {
                val items = service.getAnimeListItemsByUserId(id)
                return Transformations.map(items) { response ->
                    runBlocking(Dispatchers.IO) {
                        if (response is ApiSuccessResponse<List<AnimeListItem>>) {
                            val animes = response.body.map {
                                async {
                                    service.getAnimeByIdDeffered(it.animeId).await()
                                }
                            }.map {
                                it.await().body()!!
                            }
                            animeDao.insertAnimes(*animes.toTypedArray())
                        }
                    }
                    return@map response
                }
            }

        }.asLiveData()
    }
}
