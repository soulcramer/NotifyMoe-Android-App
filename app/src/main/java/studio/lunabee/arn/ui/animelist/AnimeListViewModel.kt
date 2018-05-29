package studio.lunabee.arn.ui.animelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import androidx.navigation.findNavController
import studio.lunabee.arn.repository.AnimeListRepository
import studio.lunabee.arn.util.AbsentLiveData
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.animelist.AnimeListItem
import javax.inject.Inject

class AnimeListViewModel @Inject constructor(
    animeListRepository: AnimeListRepository
) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val nick: LiveData<String>
        get() = _userId

    val items: LiveData<Resource<List<SimpleAnimeListItem>>> = Transformations.switchMap(_userId) { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            Transformations.map(animeListRepository.loadAnimeListItemsByUserId(id)) {
                return@map when (it) {
                    is Resource.Failure -> Resource.Failure(it.msg)
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Success -> {
                        val items = processData(it.data)
                        Resource.Success(items) as Resource<List<SimpleAnimeListItem>>
                    }
                }
            }
        }
    }

    private fun toFaItem(it: AnimeListItem): SimpleAnimeListItem {
        return SimpleAnimeListItem().apply {
            title = it.animeId
            withIdentifier(it.animeId.hashCode().toLong())
            withOnItemClickListener { v, _, item, _ ->
                val direction = AnimeListFragmentDirections.ItemClick(item.title)
                v?.findNavController()?.navigate(direction)
                true
            }
        }
    }

    private fun processData(animeListItems: List<AnimeListItem>?): List<SimpleAnimeListItem> {
        return animeListItems?.asSequence()
            ?.filter {
                it.status == "watching"
            }?.map(::toFaItem)
            ?.toList() ?: emptyList()
    }

    fun setUserId(nick: String?) {
        if (_userId.value != nick) {
            _userId.value = nick
        }
    }
}
