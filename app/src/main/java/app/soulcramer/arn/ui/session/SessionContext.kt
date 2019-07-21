package app.soulcramer.arn.ui.session

import app.soulcramer.arn.ui.common.BaseAction
import app.soulcramer.arn.ui.common.BaseState

interface SessionContext {
    sealed class Action : BaseAction {
        data class LogIn(val userId: String) : Action()
        object LogOut : Action()
        object CheckHasLoggedUser : Action()
    }

    data class State(
        val loggedUserId: String? = "VJOK1ckvx"
    ) : BaseState
}