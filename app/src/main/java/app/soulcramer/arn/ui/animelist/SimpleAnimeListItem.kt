package app.soulcramer.arn.ui.animelist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import app.soulcramer.arn.GlideApp
import app.soulcramer.arn.R
import app.soulcramer.arn.model.animelist.MappedAnimeItem
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.item_simple_animelist.view.*

class SimpleAnimeListItem : AbstractItem<SimpleAnimeListItem, SimpleAnimeListItem.ViewHolder>() {
    lateinit var animeListItem: MappedAnimeItem

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
                titleTextView.text = animeListItem.animeName
                GlideApp.with(this@ViewHolder.itemView)
                    .saturateOnLoad()
                    .load("https://media.notify.moe/images/anime/medium/${animeListItem.animeId}@2.webp")
                    .into(animeCoverImageView)
            }
        }

        override fun unbindView(item: SimpleAnimeListItem) {
            titleTextView.text = null
        }
    }
}
