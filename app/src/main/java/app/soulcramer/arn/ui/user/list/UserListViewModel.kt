package app.soulcramer.arn.ui.user.list

import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.interactor.SearchUsers
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseViewModel
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import app.soulcramer.arn.ui.user.list.UserListContext.Action
import app.soulcramer.arn.ui.user.list.UserListContext.Action.SearchUser
import app.soulcramer.arn.ui.user.list.UserListContext.State

class UserListViewModel(private val searchUsers: SearchUsers) : BaseViewModel<Action, State>(State()) {

    override suspend fun onHandle(action: Action) {
        when (action) {
            is SearchUser -> {
                searchUser(action.searchedNickname, action.forceRefresh)
            }
        }
    }

    private suspend fun searchUser(filter: String, forceRefresh: Boolean) {
        setFilter(filter, forceRefresh)
        val result = searchUsers(filter)
        updateState { it.copy(isRefreshing = false) }
        updateStateOnResultType(result)
    }

    private fun setFilter(filter: String, forceRefresh: Boolean) {
        updateState { currentState ->
            currentState.copy(filter = currentState.getFilter(filter, forceRefresh),
                filterActive = filter.isNotEmpty(),
                isRefreshing = forceRefresh)
        }
    }

    private fun State.getFilter(filter: String, forceRefresh: Boolean): String {
        return if (forceRefresh) this.filter else filter
    }

    private fun updateStateOnResultType(result: Result<List<User>>) {
        updateState { state ->
            return@updateState when (result) {
                is Failure -> state.copy(status = Error)
                is Result.Loading -> state.copy(status = Loading)
                is Success<List<User>> -> state.copyWithNewUserList(result.data)
            }
        }
    }

    private fun State.copyWithNewUserList(users: List<User>): State {
        return copy(
            users = users,
            status = Data
        )
    }
}
