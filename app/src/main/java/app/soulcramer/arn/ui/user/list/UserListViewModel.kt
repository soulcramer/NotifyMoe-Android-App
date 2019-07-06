package app.soulcramer.arn.ui.user.list

import android.accounts.NetworkErrorException
import androidx.paging.PagedList
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.interactor.SearchUsers
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseViewModel
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import app.soulcramer.arn.ui.common.NetworkError
import app.soulcramer.arn.ui.user.list.UserListContext.Action
import app.soulcramer.arn.ui.user.list.UserListContext.Action.SearchUser
import app.soulcramer.arn.ui.user.list.UserListContext.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class UserListViewModel(private val searchUsers: SearchUsers) : BaseViewModel<Action, State>(State()) {

    private var searchJob: Job? = null

    private val boundaryCallback = object : PagedList.BoundaryCallback<User>() {
        override fun onZeroItemsLoaded() {
            Timber.d("test")
        }

        override fun onItemAtEndLoaded(itemAtEnd: User) {
            Timber.d("test")
        }

        override fun onItemAtFrontLoaded(itemAtFront: User) {
            Timber.d("test")
        }
    }

    init {
        handle(SearchUser())
    }

    override suspend fun onHandle(action: Action) {
        when (action) {
            is SearchUser -> coroutineScope {
                searchJob?.cancel()
                searchJob = launch {
                    delay(SEARCH_DEBOUNCE_PERIOD)
                    searchUser(action)
                }
            }
        }
    }

    private suspend fun searchUser(action: SearchUser) {
        val filter = action.searchedNickname
        val forceRefresh = action.forceRefresh
        setFilter(filter, forceRefresh)
        val params = SearchUsers.Params(
            sort = state.value!!.sort,
            filter = state.value!!.filter,
            forceRefresh = forceRefresh,
            pagingConfig = PAGING_CONFIG,
            boundaryCallback = boundaryCallback
        )
        val result = searchUsers(params)
        updateState { it.copy(isRefreshing = false) }
        updateStateOnResultType(result)
    }

    private fun setFilter(filter: String, forceRefresh: Boolean) {
        updateState { currentState ->
            currentState.copy(
                filter = currentState.getFilter(filter, forceRefresh),
                filterActive = filter.isNotEmpty(),
                isRefreshing = forceRefresh
            )
        }
    }

    private fun State.getFilter(filter: String, forceRefresh: Boolean): String {
        return if (forceRefresh) this.filter else filter
    }

    private fun updateStateOnResultType(result: Result<PagedList<User>>) {
        updateState { state ->
            return@updateState when (result) {
                is Failure -> state.copyWithError(result.exception)
                is Result.Loading -> state.copy(status = Loading)
                is Success<PagedList<User>> -> state.copyWithNewUserList(result.data)
            }
        }
    }

    private fun State.copyWithError(exception: Exception): State {
        return when (exception) {
            is NetworkErrorException -> copy(status = NetworkError)
            else -> copy(status = Error)
        }
    }

    private fun State.copyWithNewUserList(users: PagedList<User>): State {
        return copy(
            users = users,
            status = Data
        )
    }

    companion object {
        private val PAGING_CONFIG = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(true)
            .build()

        private const val SEARCH_DEBOUNCE_PERIOD = 300L
    }
}
