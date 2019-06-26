package app.soulcramer.arn.ui.user.list

import app.soulcramer.arn.domain.SortOption
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.BaseAction
import app.soulcramer.arn.ui.common.BaseState
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.ViewState

interface UserListContext {
    sealed class Action : BaseAction {
        data class SearchUser(
            val searchedNickname: String = "",
            val forceRefresh: Boolean = false
        ) : Action()
    }

    data class State(
        val users: List<User> = emptyList(),
        val isRefreshing: Boolean = false,
        val status: ViewState = Data,
        val filterActive: Boolean = false,
        val filter: String = "",
        val availableSorts: List<SortOption> = listOf(SortOption.ALPHABETICAL),
        val sort: SortOption = SortOption.ALPHABETICAL
    ) : BaseState
}