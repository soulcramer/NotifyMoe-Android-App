package app.soulcramer.arn.ui.common

import android.graphics.drawable.Drawable
import android.view.MenuItem
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

fun MenuItem.asGlideTarget() = object : SimpleTarget<Drawable>() {
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        icon = resource
    }
}