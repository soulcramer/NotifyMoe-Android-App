package app.soulcramer.arn.ui.user.list

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import app.soulcramer.arn.PlaceholderUserItemBindingModel_
import app.soulcramer.arn.UserItemBindingModel_
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.filter
import app.soulcramer.arn.header
import app.soulcramer.arn.ui.common.SortPopupMenuListener
import app.soulcramer.arn.ui.common.epoxy.EpoxyModelProperty
import app.soulcramer.arn.ui.common.popupMenuItemIdToSortOption
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController

class UserListEpoxyController(
    private val textCreator: UserListTextCreator
) : PagedListEpoxyController<User>(
    Handler(Looper.getMainLooper()),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    init {
        isDebugLoggingEnabled = true
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }

    var callbacks: Callbacks? = null

    var viewState by EpoxyModelProperty { UserListContext.State() }

    override fun buildItemModel(currentPosition: Int, item: User?): EpoxyModel<*> {
        return if (item != null) {
            UserItemBindingModel_().apply {
                id(item.id)
                nickname(item.nickname)
                avatarUrl(item.avatarUrl)
                contentDescription("${item.nickname} info")
                avatarTransitionName("user_${item.id}")
                clickListener { _ ->
                    callbacks?.onUserClicked(item)
                }
            }
        } else {
            PlaceholderUserItemBindingModel_().id("item_placeholder_$currentPosition")
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        header {
            id("header")
            titleString(textCreator.showHeaderCount(models.size, viewState.filterActive))
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
        super.addModels(models)
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

    interface Callbacks {
        fun onUserClicked(item: User)
        fun onFilterChanged(filter: String)
    }
}