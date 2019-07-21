package app.soulcramer.arn.ui.user

import app.soulcramer.arn.ui.common.BaseAction
import app.soulcramer.arn.ui.common.BaseState
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.ViewState

interface UserContext {
    sealed class Action : BaseAction {
        data class LoadUser(val userId: String) : Action()
    }

    data class State(
        val name: String = "",
        val title: String = "",
        val avatar: String = "",
        val cover: String = "",
        val status: ViewState = Data
    ) : BaseState
}