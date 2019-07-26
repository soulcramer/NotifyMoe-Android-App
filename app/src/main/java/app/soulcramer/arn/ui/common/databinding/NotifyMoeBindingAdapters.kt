package app.soulcramer.arn.ui.common.databinding

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import app.soulcramer.arn.GlideApp
import app.soulcramer.arn.R
import app.soulcramer.arn.common.InitialMargin
import app.soulcramer.arn.common.InitialPadding
import app.soulcramer.arn.common.optSaturateOnLoad
import app.soulcramer.arn.common.recordInitialMarginForView
import app.soulcramer.arn.common.recordInitialPaddingForView
import app.soulcramer.arn.common.requestApplyInsetsWhenAttached
import app.soulcramer.arn.util.MaxLinesToggleClickListener
import app.soulcramer.arn.util.ScrimUtil
import com.google.android.material.shape.MaterialShapeDrawable
import java.util.Objects

@BindingAdapter("maxLinesToggle")
fun maxLinesClickListener(view: TextView, oldCollapsedMaxLines: Int, newCollapsedMaxLines: Int) {
    if (oldCollapsedMaxLines != newCollapsedMaxLines) {
        // Default to collapsed
        view.maxLines = newCollapsedMaxLines
        // Now set click listener
        view.setOnClickListener(MaxLinesToggleClickListener(newCollapsedMaxLines))
    }
}

@Suppress("UNUSED_PARAMETER")
@BindingAdapter(
    "image",
    "imageSaturateOnLoad",
    requireAll = false
)
fun loadImage(
    view: ImageView,
    previousImage: String?,
    previousSaturateOnLoad: Boolean?,
    image: String?,
    saturateOnLoad: Boolean?
) {
    val requestKey = Objects.hash(image)
    view.setTag(R.id.loading, requestKey)

    if (image != null) {
        if (previousImage == image) {
            return
        }

        view.setImageDrawable(null)

        view.doOnLayout {
            if (it.getTag(R.id.loading) != requestKey) {
                // The request key is different, exit now since there's we've probably be rebound to a different
                // item
                return@doOnLayout
            }

            GlideApp.with(it)
                .optSaturateOnLoad(saturateOnLoad == null || saturateOnLoad)
                .error(R.drawable.ic_person_outline_black_24dp)
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .load(image)
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

@BindingAdapter("visibleIfNotEmpty")
fun visibleIfNotEmpty(view: View, target: CharSequence?) {
    view.isVisible = target.isNullOrEmpty()
}

@BindingAdapter("backgroundScrim")
fun backgroundScrim(view: View, color: Int) {
    view.background = ScrimUtil.makeCubicGradientScrimDrawable(color, 16, Gravity.BOTTOM)
}

@BindingAdapter("foregroundScrim")
fun foregroundScrim(view: View, color: Int) {
    view.foreground = ScrimUtil.makeCubicGradientScrimDrawable(color, 16, Gravity.BOTTOM)
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