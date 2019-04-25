package app.soulcramer.arn.ui.animelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import app.soulcramer.arn.repository.AnimeListRepository
import app.soulcramer.arn.repository.AnimeRepository
import app.soulcramer.arn.util.AbsentLiveData
import app.soulcramer.arn.vo.Error
import app.soulcramer.arn.vo.Loading
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.Success
import app.soulcramer.arn.vo.animelist.AnimeListItem

class AnimeListViewModel(
    animeListRepository: AnimeListRepository,
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val nick: LiveData<String>
        get() = _userId

    private val _watchingStatus = MutableLiveData<String>()
    val watchingStatus: LiveData<String>
        get() = _watchingStatus

    private var loadItemsByUserId: LiveData<Resource<List<AnimeListItem>>> = AbsentLiveData.create()

    private val _items: MediatorLiveData<Resource<List<SimpleAnimeListItem>>> = MediatorLiveData()
    val items: LiveData<Resource<List<SimpleAnimeListItem>>>
        get() = _items

    init {
        _items.addSource(_userId) { id ->
            if (id == null) {
                _items.postValue(null)
            } else {
                loadItemsByUserId = animeListRepository.loadItemsByUserId(id)
                _items.addSource(loadItemsByUserId) { resource ->
                    var items: List<SimpleAnimeListItem>? = null
                    resource!!.data?.apply {
                        items = processData(resource.data)
                    }
                    val value = when (resource.status) {
                        is Error -> Resource.error(resource.message!!, items)
                        is Loading -> Resource.loading(items)
                        is Success -> {
                            Resource.success(items)
                        }
                    }
                    _items.value = value
                }
            }
        }
    }

    override fun onCleared() {
        _items.removeSource(_userId)
        _items.removeSource(loadItemsByUserId)
        super.onCleared()
    }

    private fun toFaItem(it: AnimeListItem): SimpleAnimeListItem {
        return SimpleAnimeListItem().apply {
            animeListItem = it
            withIdentifier(it.animeId.hashCode().toLong())
            withOnItemClickListener { v, _, item, _ ->
                val direction = AnimeListFragmentDirections.itemClick(item.animeListItem?.animeId ?: "")
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
