package app.soulcramer.arn.ui.user.list

import android.content.Context
import androidx.core.text.parseAsHtml
import app.soulcramer.arn.R

class UserListTextCreator(private val context: Context) {
    fun showHeaderCount(count: Int, filtered: Boolean = false): CharSequence = when {
        filtered -> context.resources.getQuantityString(R.plurals.header_user_count_filtered, count, count)
        else -> context.resources.getQuantityString(R.plurals.header_user_count, count, count)
    }.parseAsHtml()
}
