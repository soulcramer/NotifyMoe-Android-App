package app.soulcramer.arn.ui.user

import app.soulcramer.arn.domain.interactor.Error
import app.soulcramer.arn.domain.interactor.GetUser
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseViewModel
import app.soulcramer.arn.ui.user.UserContext.Action
import app.soulcramer.arn.ui.user.UserContext.Action.LoadUser
import app.soulcramer.arn.ui.user.UserContext.State

class UserViewModel(private val getUser: GetUser) : BaseViewModel<Action, State>(State()) {

    override suspend fun onHandle(action: Action) {
        when (action) {
            is LoadUser -> {
                val result = getUser(action.userId)
                updateState { state ->
                    if (result !is Success<User>) return@updateState state.copy(status = Error)
                    val user = result.data
                    state.copy(
                        title = user.title,
                        name = user.name,
                        avatar = user.avatar,
                        cover = user.cover,
                        status = app.soulcramer.arn.domain.interactor.Success
                    )
                }
            }
        }
        // No action defined yet
    }
}
