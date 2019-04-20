package app.soulcramer.arn.ui.common

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil
import kotlin.math.floor

class EqualSpacingItemDecoration @JvmOverloads constructor(private val spacing: Int,
    private var displayMode: Int = -1) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int) {

        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }

        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = spacing
            }
            VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
                outRect.bottom = if (position == itemCount - 1) spacing else 0
            }
            GRID -> if (layoutManager is GridLayoutManager) {
                val gridLayoutManager = layoutManager as GridLayoutManager?
                val cols = gridLayoutManager!!.spanCount
                val currentColumn = position % cols
                val rows = ceil(1.0 * itemCount / cols).toInt()

                outRect.left = spacing - currentColumn * spacing / cols
                outRect.right = (currentColumn + 1) * spacing / cols
                outRect.top = spacing
                outRect.bottom = if (position / cols == rows - 1) spacing else 0
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        return if (layoutManager!!.canScrollHorizontally()) HORIZONTAL else VERTICAL
    }

    companion object {
        const val HORIZONTAL: Int = 0
        const val VERTICAL: Int = 1
        const val GRID: Int = 2
    }
}

// Todo Remove these extensions when https://github.com/android/android-ktx/issues/132 it is closed
fun Int.toDp(displayMetrics: DisplayMetrics): Float = this.toFloat() / displayMetrics.density

fun Float.dpToPx(displayMetrics: DisplayMetrics): Int =
    (this * displayMetrics.density).round()

private fun Float.round(): Int = (if (this < 0) ceil(this - 0.5f) else floor(this + 0.5f)).toInt()