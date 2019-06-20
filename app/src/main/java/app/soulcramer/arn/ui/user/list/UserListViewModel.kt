package app.soulcramer.arn.ui.user.list

import app.soulcramer.arn.domain.interactor.GetUser
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseViewModel
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import app.soulcramer.arn.ui.user.list.UserListContext.Action
import app.soulcramer.arn.ui.user.list.UserListContext.Action.Refresh
import app.soulcramer.arn.ui.user.list.UserListContext.Action.SearchUsers
import app.soulcramer.arn.ui.user.list.UserListContext.State

class UserListViewModel(private val getUser: GetUser) : BaseViewModel<Action, State>(State()) {

    override suspend fun onHandle(action: Action) {
        when (action) {
            is SearchUsers -> {
//                val result = getUser(action.userId)
//                updateStateOnResultType(result)
            }
            is Refresh -> {}
        }
    }

    private fun updateStateOnResultType(result: Result<List<User>>) {
        updateState { state ->
            return@updateState when (result) {
                is Failure -> state.copy(status = Error)
                is Result.Loading -> state.copy(status = Loading)
                is Success<List<User>> -> state.copyWithNewUser(result.data)
            }
        }
    }

    private fun State.copyWithNewUser(users: List<User>): State {
        return copy(
            users = users,
            status = Data
        )
    }
}
