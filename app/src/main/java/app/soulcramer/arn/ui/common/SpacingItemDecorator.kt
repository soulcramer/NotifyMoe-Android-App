package app.soulcramer.arn.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecorator(left: Int, top: Int, right: Int, bottom: Int) : RecyclerView.ItemDecoration() {

    constructor(spacing: Int) : this(spacing, spacing, spacing, spacing)

    private val spacingRect = Rect()

    init {
        spacingRect.set(left, top, right, bottom)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(spacingRect)
    }
}