package app.soulcramer.arn.ui.anime

import app.soulcramer.arn.data.AnimeRepository
import app.soulcramer.arn.ui.anime.MangaDetailsContext.Action
import app.soulcramer.arn.ui.anime.MangaDetailsContext.Action.LoadMangaInformations
import app.soulcramer.arn.ui.anime.MangaDetailsContext.State
import app.soulcramer.arn.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeDetailViewModel(
    private val animeRepository: AnimeRepository,
    private val animeId: String
) : BaseViewModel<Action, State>(State()) {

    private suspend fun loadAnimeInformations() {
        //        updateState { state ->
        //            state.copy(
        //                isLoading = true
        //            )
        //        }
        val anime = withContext(Dispatchers.IO) {
            animeRepository.loadAnimeById(animeId)
        }

        if (anime != null) {
            updateState { state ->
                state.copy(
                    title = anime.title.canonical,
                    genres = anime.genres,
                    poster = "https://media.notify.moe/images/anime/medium/${anime.id}@2.webp",
                    type = anime.type,
                    summary = anime.summary
                )
            }
        }
    }

    override suspend fun onHandle(action: Action) {
        when (action) {
            is LoadMangaInformations -> loadAnimeInformations()
        }
    }
}