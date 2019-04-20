package app.soulcramer.arn.ui.common

import android.graphics.ColorMatrix

/**
 * An extension to [ColorMatrix] which implements the Material image loading pattern
 */
class ImageLoadingColorMatrix : ColorMatrix() {
    private val elements = FloatArray(20)

    var saturationFraction = 1f
        set(value) {
            System.arraycopy(array, 0, elements, 0, 20)

            // Taken from ColorMatrix.setSaturation. We can't use that though since it resets the matrix
            // before applying the values
            val invSat = 1 - value
            val r = 0.213f * invSat
            val g = 0.715f * invSat
            val b = 0.072f * invSat

            elements[0] = r + value
            elements[1] = g
            elements[2] = b
            elements[5] = r
            elements[6] = g + value
            elements[7] = b
            elements[10] = r
            elements[11] = g
            elements[12] = b + value

            set(elements)
            field = value
        }

    var alphaFraction = 1f
        set(value) {
            System.arraycopy(array, 0, elements, 0, 20)
            elements[18] = value
            set(elements)
            field = value
        }

    var darkenFraction = 1f
        set(value) {
            System.arraycopy(array, 0, elements, 0, 20)

            // We substract to make the picture look darker, it will automatically clamp
            val darkening = (1 - value) * MAX_DARKEN_PERCENTAGE * 255
            elements[4] = -darkening
            elements[9] = -darkening
            elements[14] = -darkening

            set(elements)
            field = value
        }

    companion object {
        private val saturationFloatProp = object : FloatProp<ImageLoadingColorMatrix>("saturation") {
            override operator fun get(o: ImageLoadingColorMatrix): Float = o.saturationFraction
            override operator fun set(o: ImageLoadingColorMatrix, value: Float) {
                o.saturationFraction = value
            }
        }

        private val alphaFloatProp = object : FloatProp<ImageLoadingColorMatrix>("alpha") {
            override operator fun get(o: ImageLoadingColorMatrix): Float = o.alphaFraction
            override operator fun set(o: ImageLoadingColorMatrix, value: Float) {
                o.alphaFraction = value
            }
        }

        private val darkenFloatProp = object : FloatProp<ImageLoadingColorMatrix>("darken") {
            override operator fun get(o: ImageLoadingColorMatrix): Float = o.darkenFraction
            override operator fun set(o: ImageLoadingColorMatrix, value: Float) {
                o.darkenFraction = value
            }
        }

        val PROP_SATURATION = createFloatProperty(saturationFloatProp)
        val PROP_ALPHA = createFloatProperty(alphaFloatProp)
        val PROP_DARKEN = createFloatProperty(darkenFloatProp)

        // This means that we darken the image by 20%
        private const val MAX_DARKEN_PERCENTAGE = 0.20f
    }
}