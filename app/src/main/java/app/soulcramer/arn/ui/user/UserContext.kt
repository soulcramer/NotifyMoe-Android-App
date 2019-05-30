package app.soulcramer.arn.ui.user

import app.soulcramer.arn.domain.interactor.Status
import app.soulcramer.arn.domain.interactor.Success
import app.soulcramer.arn.ui.common.BaseAction
import app.soulcramer.arn.ui.common.BaseState

interface UserContext {
    sealed class Action : BaseAction {
        data class LoadUser(val userId: String) : Action()
    }

    data class State(
        val name: String = "",
        val title: String = "",
        val avatar: String = "",
        val cover: String = "",
        val status: Status = Success
    ) : BaseState
}