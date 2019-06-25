package app.soulcramer.arn.ui.user

import app.soulcramer.arn.domain.interactor.GetUser
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseViewModel
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import app.soulcramer.arn.ui.user.UserContext.Action
import app.soulcramer.arn.ui.user.UserContext.Action.LoadUser
import app.soulcramer.arn.ui.user.UserContext.State

class UserViewModel(
    private val getUser: GetUser,
    initialState: State = State()
) : BaseViewModel<Action, State>(initialState) {

    override suspend fun onHandle(action: Action) {
        when (action) {
            is LoadUser -> {
                val result = getUser(action.userId)
                updateStateOnResultType(result)
            }
        }
    }

    private fun updateStateOnResultType(result: Result<User>) {
        updateState { state ->
            return@updateState when (result) {
                is Failure -> state.copy(status = Error)
                is Result.Loading -> state.copy(status = Loading)
                is Success<User> -> state.copyWithNewUser(result.data)
            }
        }
    }

    private fun State.copyWithNewUser(user: User): State {
        return copy(
            title = user.title,
            name = user.name,
            avatar = user.avatar,
            cover = user.cover,
            status = Data
        )
    }
}
