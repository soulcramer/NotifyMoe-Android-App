package studio.lunabee.arn.ui.animelist

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.simple_animelist_item.view.*
import studio.lunabee.arn.R

class SimpleAnimeListItem : AbstractItem<SimpleAnimeListItem, SimpleAnimeListItem.ViewHolder>() {
    var title: String = ""

    //The unique ID for this type of item
    override fun getType(): Int = R.id.simple_animelist_item

    //The layout to be used for this type of item
    override fun getLayoutRes(): Int = R.layout.simple_animelist_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    @Suppress("JoinDeclarationAndAssignment")
    class ViewHolder(view: View) : FastAdapter.ViewHolder<SimpleAnimeListItem>(view) {

        private val titleTextView: TextView

        init {
            titleTextView = view.titleTextView
        }

        override fun bindView(item: SimpleAnimeListItem, payloads: List<Any>) {
            titleTextView.text = item.title
        }

        override fun unbindView(item: SimpleAnimeListItem) {
            titleTextView.text = null
        }
    }
}
