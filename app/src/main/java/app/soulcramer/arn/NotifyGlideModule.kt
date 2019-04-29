package app.soulcramer.arn

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888
import com.bumptech.glide.load.DecodeFormat.PREFER_RGB_565
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class NotifyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager

        val options = RequestOptions()
            .format(if (am.isLowRamDevice) PREFER_RGB_565 else PREFER_ARGB_8888)

        builder.setDefaultRequestOptions(options)
    }

    override fun isManifestParsingEnabled(): Boolean = false
}