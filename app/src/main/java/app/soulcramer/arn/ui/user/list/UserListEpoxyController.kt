package app.soulcramer.arn.ui.user.list

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.filter
import app.soulcramer.arn.header
import app.soulcramer.arn.ui.common.SortPopupMenuListener
import app.soulcramer.arn.ui.common.popupMenuItemIdToSortOption
import app.soulcramer.arn.userItem
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.TypedEpoxyController
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class UserListEpoxyController(
    private val context: Context
) : TypedEpoxyController<List<User>>(
    Handler(Looper.getMainLooper()),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
), KoinComponent {

    private val textCreator: UserListTextCreator by inject { parametersOf(context) }

    var callbacks: Callbacks? = null

    var viewState: UserListContext.State = UserListContext.State()

    override fun buildModels(users: List<User>) {

        header {
            id("header")
            titleString(textCreator.showHeaderCount(users.size, viewState.filterActive))
        }

        filter {
            id("filters")
            filter(viewState.filter)
            watcher(filterTextWatcher())

            popupMenuListener(SortPopupMenuListener(viewState.sort, viewState.availableSorts))
            popupMenuClickListener { menuItem ->
                popupMenuItemIdToSortOption(menuItem.itemId)
                    ?: throw IllegalArgumentException("Selected sort option is null")
                true
            }
        }

        addUsers(users)
    }

    private fun filterTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                callbacks?.onFilterChanged(s?.toString() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        }
    }

    private fun addUsers(users: List<User>) {
        users.forEach { user ->
            userItem {
                id(user.id)
                nickname(user.nickname)
                avatarUrl(user.avatarUrl)
                contentDescription("${user.nickname} info")
                avatarTransitionName("user_${user.id}")
                clickListener { _ ->
                    callbacks?.onUserClicked(user)
                }
            }
        }
    }

    interface Callbacks {
        fun onUserClicked(item: User)
        fun onFilterChanged(filter: String)
    }
}