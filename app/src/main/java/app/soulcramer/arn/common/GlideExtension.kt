package app.soulcramer.arn.common

import android.graphics.drawable.Drawable
import app.soulcramer.arn.GlideRequest
import app.soulcramer.arn.GlideRequests

fun GlideRequests.optSaturateOnLoad(saturateOnLoad: Boolean): GlideRequest<Drawable> = when {
    saturateOnLoad -> saturateOnLoad()
    else -> asDrawable()
}