package studio.lunabee.arn.ui.animelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
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
    val mItems: LiveData<Resource<List<AnimeListItem>>> = Transformations.switchMap(_userId) { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            animeListRepository.loadAnimeListItemsByUserId(id)
        }
    }

    fun setUserId(nick: String?) {
        if (_userId.value != nick) {
            _userId.value = nick
        }
    }
}