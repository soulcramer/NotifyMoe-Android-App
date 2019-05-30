package app.soulcramer.arn.ui.common.databinding

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import app.soulcramer.arn.GlideApp
import app.soulcramer.arn.common.doOnApplyWindowInsets
import app.soulcramer.arn.common.resolveThemeReferenceResId
import app.soulcramer.arn.util.MaxLinesToggleClickListener
import app.soulcramer.arn.util.ScrimUtil
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import java.util.Objects
import kotlin.math.roundToInt

@BindingAdapter("maxLinesToggle")
fun maxLinesClickListener(view: TextView, oldCollapsedMaxLines: Int, newCollapsedMaxLines: Int) {
    if (oldCollapsedMaxLines != newCollapsedMaxLines) {
        // Default to collapsed
        view.maxLines = newCollapsedMaxLines
        // Now set click listener
        view.setOnClickListener(MaxLinesToggleClickListener(newCollapsedMaxLines))
    }
}

@BindingAdapter(
    "url",
    "imageSaturateOnLoad",
    requireAll = false
)
fun loadPoster(
    view: ImageView,
    url: String?,
    saturateOnLoad: Boolean?
) {
    loadImage(view, url, saturateOnLoad, "poster")
}

@BindingAdapter(
    "url",
    "imageSaturateOnLoad",
    requireAll = false
)
fun loadBackdrop(
    view: ImageView,
    url: String?,
    saturateOnLoad: Boolean?
) = loadImage(view, url, saturateOnLoad, "backdrop")

@BindingAdapter(
    "url",
    "imageSaturateOnLoad",
    requireAll = false
)
fun loadImage(
    view: ImageView,
    url: String?,
    saturateOnLoad: Boolean?
) = loadImage(view, url, saturateOnLoad, "backdrop")

private inline fun loadImage(
    view: ImageView,
    url: String?,
    saturateOnLoad: Boolean?,
    type: String
) {
    if (url != null) {
        val requestKey = Objects.hash(url, type)
        view.setTag("loading".hashCode(), requestKey)

        view.doOnLayout {
            if (it.getTag("loading".hashCode()) != requestKey) {
                // The request key is different, exit now since there's we've probably be rebound to a different
                // item
                return@doOnLayout
            }

            GlideApp.with(it)
                .let { r ->
                    if (saturateOnLoad == null || saturateOnLoad) {
                        // If we don't have a value, or we're explicitly set the yes, saturate on load
                        r.saturateOnLoad()
                    } else {
                        r.asDrawable()
                    }
                }
                .load(url)
                .thumbnail(
                    GlideApp.with(view)
                        .let { tr ->
                            if (saturateOnLoad == null || saturateOnLoad) {
                                // If we don't have a value, or we're explicitly set the yes, saturate on load
                                tr.saturateOnLoad()
                            } else {
                                tr.asDrawable()
                            }
                        }
                        .load(url)
                )
                .into(view)
        }
    } else {
        GlideApp.with(view).clear(view)
        view.setImageDrawable(null)
    }
}

@BindingAdapter("visibleIfNotNull")
fun visibleIfNotNull(view: View, target: Any?) {
    view.isVisible = target != null
}

@BindingAdapter("visible")
fun visible(view: View, value: Boolean) {
    view.isVisible = value
}

@BindingAdapter("srcRes")
fun imageViewSrcRes(view: ImageView, drawableRes: Int) {
    if (drawableRes != 0) {
        view.setImageResource(drawableRes)
    } else {
        view.setImageDrawable(null)
    }
}

@BindingAdapter("backgroundScrim")
fun backgroundScrim(view: View, color: Int) {
    view.background = ScrimUtil.makeCubicGradientScrimDrawable(color, 16, Gravity.BOTTOM)
}

@BindingAdapter("foregroundScrim")
fun foregroundScrim(view: View, color: Int) {
    view.foreground = ScrimUtil.makeCubicGradientScrimDrawable(color, 16, Gravity.BOTTOM)
}

@BindingAdapter("materialBackdropBackgroundRadius")
fun materialBackdropBackground(view: View, radius: Float) {
    view.background = MaterialShapeDrawable().apply {
        fillColor = ColorStateList.valueOf(Color.WHITE)
        shapeAppearanceModel.setTopLeftCorner(CornerFamily.ROUNDED, radius.toInt())
        shapeAppearanceModel.setTopRightCorner(CornerFamily.ROUNDED, radius.toInt())
    }
}

@BindingAdapter("topCornerOutlineProvider")
fun topCornerOutlineProvider(view: View, radius: Float) {
    view.clipToOutline = true
    view.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height + radius.roundToInt(), radius)
        }
    }
}

@BindingAdapter("textAppearanceAttr")
fun textAppearanceAttr(view: TextView, textAppearanceStyleAttr: Int) {
    view.setTextAppearance(view.context.resolveThemeReferenceResId(textAppearanceStyleAttr))
}

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun applySystemWindows(
    view: View,
    systemWindowLeft: Boolean,
    systemWindowTop: Boolean,
    systemWindowRight: Boolean,
    systemWindowBottom: Boolean
) {
    view.doOnApplyWindowInsets { v, insets, paddingState ->
        val left = if (systemWindowLeft) insets.systemWindowInsetLeft else 0
        val top = if (systemWindowTop) insets.systemWindowInsetTop else 0
        val right = if (systemWindowRight) insets.systemWindowInsetRight else 0
        val bottom = if (systemWindowBottom) insets.systemWindowInsetBottom else 0
        v.setPadding(
            paddingState.left + left,
            paddingState.top + top,
            paddingState.right + right,
            paddingState.bottom + bottom
        )
    }
}