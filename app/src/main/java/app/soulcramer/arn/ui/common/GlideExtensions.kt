package app.soulcramer.arn.ui.common

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.annotation.GlideType
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.BaseRequestOptions

@GlideExtension
object GlideExtensions {
    @GlideOption
    @JvmStatic
    fun round(options: BaseRequestOptions<*>, size: Int): BaseRequestOptions<*> {
        return options.circleCrop().override(size)
    }

    @JvmStatic
    @GlideType(Drawable::class)
    fun saturateOnLoad(requestBuilder: RequestBuilder<Drawable>): RequestBuilder<Drawable> {
        return requestBuilder.transition(DrawableTransitionOptions.with(SaturationTransitionFactory()))
    }
}