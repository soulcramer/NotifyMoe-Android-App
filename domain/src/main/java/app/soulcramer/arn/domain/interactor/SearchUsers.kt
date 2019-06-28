package app.soulcramer.arn.domain.interactor

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.PagedList
import app.soulcramer.arn.domain.SortOption
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository
import java.util.concurrent.Executors

/**
 * Use case used for searching a list of [User] instances by their nickname from the [UserRepository]
 */
class SearchUsers(private val userRepository: UserRepository) : PagingUseCase<SearchUsers.Params, User>() {

    override suspend fun execute(parameters: Params): PagedList<User> {
        val source = userRepository.searchUser(parameters.filter, parameters.forceRefresh).create()
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