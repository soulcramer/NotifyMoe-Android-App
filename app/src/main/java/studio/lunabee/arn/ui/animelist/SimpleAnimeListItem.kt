package studio.lunabee.arn.ui.animelist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_simple_animelist.view.*
import studio.lunabee.arn.R

class SimpleAnimeListItem : AbstractItem<SimpleAnimeListItem, SimpleAnimeListItem.ViewHolder>() {
    var title: String = ""

    //The unique ID for this type of item
    override fun getType(): Int = R.id.simple_animelist_item

    //The layout to be used for this type of item
    override fun getLayoutRes(): Int = R.layout.item_simple_animelist

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    @Suppress("JoinDeclarationAndAssignment")
    class ViewHolder(view: View) : FastAdapter.ViewHolder<SimpleAnimeListItem>(view) {

        private val titleTextView: TextView
        private val animeCoverImageView: ImageView

        init {
            titleTextView = view.titleTextView
            animeCoverImageView = view.animeCoverImageView
        }

        override fun bindView(item: SimpleAnimeListItem, payloads: List<Any>) {
            item.apply {
                titleTextView.text = title
                Picasso.get()
                    .load("https://media.notify.moe/images/anime/medium/$title@2.webp")
                    .into(animeCoverImageView)
            }
        }

        override fun unbindView(item: SimpleAnimeListItem) {
            titleTextView.text = null
        }
    }
}
