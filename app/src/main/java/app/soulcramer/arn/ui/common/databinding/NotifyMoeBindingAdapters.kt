package app.soulcramer.arn.ui.common.databinding

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import app.soulcramer.arn.GlideApp
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
fun View.applySystemWindowInsetsPadding(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, padding, _ ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        view.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )
    }
}

@BindingAdapter(
    "marginLeftSystemWindowInsets",
    "marginTopSystemWindowInsets",
    "marginRightSystemWindowInsets",
    "marginBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsMargin(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, _, margin ->
        val left = if (applyLeft) insets.systemWindowInsetLeft else 0
        val top = if (applyTop) insets.systemWindowInsetTop else 0
        val right = if (applyRight) insets.systemWindowInsetRight else 0
        val bottom = if (applyBottom) insets.systemWindowInsetBottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = margin.left + left
            topMargin = margin.top + top
            rightMargin = margin.right + right
            bottomMargin = margin.bottom + bottom
        }
    }
}

fun View.doOnApplyWindowInsets(block: (View, WindowInsets, InitialPadding, InitialMargin) -> Unit) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    setOnApplyWindowInsetsListener { v, insets ->
        block(v, insets, initialPadding, initialMargin)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
        ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

@BindingAdapter("layoutFullscreen")
fun View.bindLayoutFullscreen(previousFullscreen: Boolean, fullscreen: Boolean) {
    if (previousFullscreen != fullscreen && fullscreen) {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

@BindingAdapter("materialShapeElevationBackground")
fun materialShapeElevationBackground(view: View, oldValue: Boolean, value: Boolean) {
    if (oldValue != value && value) {
        val shapeDrawable = MaterialShapeDrawable.createWithElevationOverlay(view.context, view.elevation)
        view.background = shapeDrawable

        val vto = view.viewTreeObserver
        vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                shapeDrawable.z = view.z
                if (!view.isAttachedToWindow) {
                    if (vto.isAlive) {
                        vto.removeOnPreDrawListener(this)
                    } else {
                        view.viewTreeObserver.removeOnPreDrawListener(this)
                    }
                }
                return true
            }
        })
    }
}