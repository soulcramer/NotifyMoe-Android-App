package app.soulcramer.arn.ui.user.list

import android.os.Handler
import android.os.Looper
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.userItem
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.TypedEpoxyController

class UserListEpoxyController : TypedEpoxyController<List<User>>(
    Handler(Looper.getMainLooper()),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {
    var callbacks: Callbacks? = null

    override fun buildModels(users: List<User>) {
        users.forEach {
            userItem {
                id(it.id)
                nickname(it.name)
                avatarUrl(it.avatar)
                contentDescription(it.avatar)
                avatarTransitionName("user_${it.id}")
                clickListener { _ ->
                    callbacks?.onUserClicked(it)
                }
            }
        }

    }

    interface Callbacks {
        fun onUserClicked(item: User)
    }
}