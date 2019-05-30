package app.soulcramer.arn.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.use

@SuppressLint("Recycle")
fun Context.resolveThemeColor(@AttrRes resId: Int, defaultColor: Int = Color.MAGENTA): Int {
    return obtainStyledAttributes(intArrayOf(resId)).use {
        it.getColor(0, defaultColor)
    }
}

@SuppressLint("Recycle")
fun Context.resolveThemeColorStateList(@AttrRes resId: Int): ColorStateList? {
    return obtainStyledAttributes(intArrayOf(resId)).use {
        it.getColorStateList(0)
    }
}

@SuppressLint("Recycle")
fun Context.resolveThemeReferenceResId(@AttrRes resId: Int): Int {
    return obtainStyledAttributes(intArrayOf(resId)).use {
        it.getResourceIdOrThrow(0)
    }
}