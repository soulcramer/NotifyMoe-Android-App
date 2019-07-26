package app.soulcramer.arn.domain.interactor.anime

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.DataSource
import androidx.paging.PagedList
import app.soulcramer.arn.domain.SortOption
import app.soulcramer.arn.domain.interactor.PagingUseCase
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.anime.Anime
import app.soulcramer.arn.domain.repository.AnimeRepository
import java.util.concurrent.Executors

/**
 * Use case used for searching a list of [Anime] instances by their nickname from the [AnimeRepository]
 */
class SearchAnimes(private val animeRepository: AnimeRepository) : PagingUseCase<SearchAnimes.Params, Anime>() {

    override suspend fun execute(parameters: Params): Result<PagedList<Anime>> {
        val result = animeRepository.searchAnime(parameters.filter, parameters.forceRefresh)

        return when (result) {
            is Result.Success -> buildSuccessResponse(result, parameters)
            is Result.Failure -> buildFailureResponse(result, parameters)
            else -> defaultFailure()
        }
    }

    private fun defaultFailure(): Result.Failure<PagedList<Anime>> =
        Result.Failure(Exception("Couldn't search animes"))

    private fun buildFailureResponse(
        result: Result.Failure<DataSource.Factory<Int, Anime>>,
        parameters: Params
    ): Result<PagedList<Anime>> {
        val source = result.data?.create() ?: return defaultFailure()
        return Result.Failure(result.exception, buildPagedList(source, parameters))
    }

    private fun buildSuccessResponse(
        result: Result.Success<DataSource.Factory<Int, Anime>>,
        parameters: Params
    ): Result<PagedList<Anime>> {
        val source = result.data.create()
        return Result.Success(buildPagedList(source, parameters))
    }

    private fun buildPagedList(
        source: DataSource<Int, Anime>,
        parameters: Params
    ): PagedList<Anime> {
        return PagedList.Builder(source, parameters.pagingConfig)
            .setBoundaryCallback(parameters.boundaryCallback)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
            .build()
    }

    data class Params(
        val filter: String,
        val forceRefresh: Boolean,
        val sort: SortOption,
        override val pagingConfig: PagedList.Config,
        override val boundaryCallback: PagedList.BoundaryCallback<Anime>?
    ) : Parameters<Anime>
}