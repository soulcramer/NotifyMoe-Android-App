package app.soulcramer.arn.ui.session

import app.soulcramer.arn.domain.interactor.HasLoggedUser
import app.soulcramer.arn.domain.interactor.LogInUser
import app.soulcramer.arn.domain.interactor.LogOutUser
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.interactor.invoke
import app.soulcramer.arn.ui.common.BaseViewModel
import app.soulcramer.arn.ui.session.SessionContext.Action
import app.soulcramer.arn.ui.session.SessionContext.Action.*
import app.soulcramer.arn.ui.session.SessionContext.State

class SessionViewModel(
    private val logOutUser: LogOutUser,
    private val hasLoggedUser: HasLoggedUser,
    private val logInUser: LogInUser
) : BaseViewModel<Action, State>(State()) {

    override suspend fun onHandle(action: Action) {
        when (action) {
            is LogOut -> {
                logOutUser()
                val result = hasLoggedUser()
                updateStateOnResultType(result)
            }
            is CheckHasLoggedUser -> {
                val result = hasLoggedUser()
                updateStateOnResultType(result)
            }
            is LogIn -> {
                logInUser(action.userId)
                val result = hasLoggedUser()
                updateStateOnResultType(result)
            }
        }
    }

    private fun updateStateOnResultType(result: Result<Boolean>) {
        updateState { state ->
            return@updateState when (result) {
                is Success<Boolean> -> state.copy()
                else -> state.copy()
            }
        }
    }
}
