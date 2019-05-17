package app.soulcramer.arn.ui.anime

import app.soulcramer.arn.model.anime.Genre
import app.soulcramer.arn.ui.common.BaseAction
import app.soulcramer.arn.ui.common.BaseState

interface MangaDetailsContext {
    sealed class Action : BaseAction {
        object LoadMangaInformations : Action()
    }

    data class State(
        val title: String = "",
        val poster: String = "",
        val origin: String = "",
        val type: String = "",
        val genres: List<Genre> = emptyList(),
        val summary: String = "",
        val status: Boolean = false
    ) : BaseState
}