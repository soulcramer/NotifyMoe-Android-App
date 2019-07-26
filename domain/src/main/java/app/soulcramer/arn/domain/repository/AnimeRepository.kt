package app.soulcramer.arn.domain.repository

import androidx.paging.DataSource
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.model.anime.Anime

interface AnimeRepository {

    suspend fun getAnime(animeId: String): User

    suspend fun searchAnime(searchTerm: String, forceRefresh: Boolean): Result<DataSource.Factory<Int, Anime>>
}