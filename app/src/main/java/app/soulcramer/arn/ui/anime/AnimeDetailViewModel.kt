package app.soulcramer.arn.ui.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import app.soulcramer.arn.repository.AnimeRepository
import app.soulcramer.arn.util.AbsentLiveData
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.anime.Anime

class AnimeDetailViewModel(
    animeRepository: AnimeRepository
) : ViewModel() {

    private val _animeId = MutableLiveData<String>()
    val animeId: LiveData<String>
        get() = _animeId
    val anime: LiveData<Resource<Anime>> = Transformations.switchMap(_animeId) { id ->
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
