package app.soulcramer.arn.ui.user.list

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseAction
import app.soulcramer.arn.ui.common.BaseState
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.ViewState

interface UserListContext {
    sealed class Action : BaseAction {
        data class SearchUsers(val searchedNickname: String) : Action()
        object Refresh : Action()
    }

    data class State(
        val users: List<User> = emptyList(),
        val status: ViewState = Data
    ) : BaseState
}