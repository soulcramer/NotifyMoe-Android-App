package studio.lunabee.arn.ui.anime

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import studio.lunabee.arn.repository.AnimeRepository
import studio.lunabee.arn.util.AbsentLiveData
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.anime.Anime
import javax.inject.Inject

class AnimeDetailViewModel @Inject constructor(
    animeRepository: AnimeRepository
) : ViewModel() {

    private val _animeId = MutableLiveData<String>()
    val animeId: LiveData<String>
        get() = _animeId
    val anime: LiveData<Resource<List<Anime>>> = Transformations.switchMap(_animeId) { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            animeRepository.loadAnimeById(id)
        }
    }

    fun setAnimeId(id: String?) {
        if (_animeId.value != id) {
            _animeId.value = id
        }
    }
}
