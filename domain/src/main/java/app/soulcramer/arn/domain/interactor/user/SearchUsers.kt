package app.soulcramer.arn.domain.interactor.user

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.DataSource
import androidx.paging.PagedList
import app.soulcramer.arn.domain.SortOption
import app.soulcramer.arn.domain.interactor.PagingUseCase
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository
import java.util.concurrent.Executors

/**
 * Use case used for searching a list of [User] instances by their nickname from the [UserRepository]
 */
class SearchUsers(private val userRepository: UserRepository) : PagingUseCase<SearchUsers.Params, User>() {

    override suspend fun execute(parameters: Params): Result<PagedList<User>> {
        val result = userRepository.searchUser(parameters.filter, parameters.forceRefresh)

        return when (result) {
            is Result.Success -> buildSuccessResponse(result, parameters)
            is Result.Failure -> buildFailureResponse(result, parameters)
            else -> defaultFailure()
        }
    }

    private fun defaultFailure(): Result.Failure<PagedList<User>> =
        Result.Failure(Exception("Couldn't search users"))

    private fun buildFailureResponse(
        result: Result.Failure<DataSource.Factory<Int, User>>,
        parameters: Params
    ): Result<PagedList<User>> {
        val source = result.data?.create() ?: return defaultFailure()
        return Result.Failure(result.exception, buildPagedList(source, parameters))
    }

    private fun buildSuccessResponse(
        result: Result.Success<DataSource.Factory<Int, User>>,
        parameters: Params
    ): Result<PagedList<User>> {
        val source = result.data.create()
        return Result.Success(buildPagedList(source, parameters))
    }

    private fun buildPagedList(
        source: DataSource<Int, User>,
        parameters: Params
    ): PagedList<User> {
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
        override val boundaryCallback: PagedList.BoundaryCallback<User>?
    ) : Parameters<User>
}