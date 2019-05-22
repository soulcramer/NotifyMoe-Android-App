package app.soulcramer.arn.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import app.soulcramer.arn.database.AnimeDao
import app.soulcramer.arn.database.AnimeListDao
import app.soulcramer.arn.model.Resource
import app.soulcramer.arn.model.animelist.AnimeListItem
import app.soulcramer.arn.model.animelist.MappedAnimeItem
import app.soulcramer.arn.service.ApiResponse
import app.soulcramer.arn.service.ApiSuccessResponse
import app.soulcramer.arn.service.NotifyMoeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
                val result = MediatorLiveData<ApiResponse<List<AnimeListItem>>>()
                result.addSource<ApiResponse<List<AnimeListItem>>>(items) { response ->
                    GlobalScope.launch(Dispatchers.IO) {
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
                        result.postValue(response)
                    }
                }
                return result
            }

        }.asLiveData()
    }
}
