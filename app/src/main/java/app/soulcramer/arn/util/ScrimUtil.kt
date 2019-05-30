package app.soulcramer.arn.util

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.collection.LruCache

/**
 * Utility methods for creating prettier gradient scrims.
 */
object ScrimUtil {
    private val cache = LruCache<Int, Drawable>(10)

    /**
     * Creates an approximated cubic gradient using a multi-stop linear gradient. See
     * [this post](https://plus.google.com/+RomanNurik/posts/2QvHVFWrHZf) for more
     * details.
     */
    fun makeCubicGradientScrimDrawable(@ColorInt baseColor: Int, numStops: Int, gravity: Int): Drawable {
        // Generate a cache key by hashing together the inputs, based on the method described in the Effective Java book
        var cacheKeyHash = baseColor
        cacheKeyHash = 31 * cacheKeyHash + numStops
        cacheKeyHash = 31 * cacheKeyHash + gravity

        val cachedGradient = cache.get(cacheKeyHash)
        if (cachedGradient != null) {
            return cachedGradient
        }

        val paintDrawable = PaintDrawable()
        paintDrawable.shape = RectShape()

        val stopColors = IntArray(numStops)
        val alpha = Color.alpha(baseColor)

        for (i in 0 until numStops) {
            val x = i * 1f / (numStops - 1)
            val opacity = Math.pow(x.toDouble(), 3.0).toFloat()
            stopColors[i] = (Math.round(alpha * opacity).coerceIn(0, 255) shl 24) or (baseColor and 0xFFFFFF)
        }

        val x0: Float
        val x1: Float
        val y0: Float
        val y1: Float
        when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.LEFT -> {
                x0 = 1f
                x1 = 0f
            }
            Gravity.RIGHT -> {
                x0 = 0f
                x1 = 1f
            }
            else -> {
                x0 = 0f
                x1 = 0f
            }
        }
        when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.TOP -> {
                y0 = 1f
                y1 = 0f
            }
            Gravity.BOTTOM -> {
                y0 = 0f
                y1 = 1f
            }
            else -> {
                y0 = 0f
                y1 = 0f
            }
        }

        paintDrawable.shaderFactory = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(
                    width * x0,
                    height * y0,
                    width * x1,
                    height * y1,
                    stopColors,
                    null,
                    Shader.TileMode.CLAMP)
            }
        }
        cache.put(cacheKeyHash, paintDrawable)
        return paintDrawable
    }
}