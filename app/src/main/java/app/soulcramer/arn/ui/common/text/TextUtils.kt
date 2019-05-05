package app.soulcramer.arn.ui.common.text

import android.content.Context
import android.text.style.TextAppearanceSpan
import android.util.TypedValue

private val typedValue = TypedValue()

fun textAppearanceSpanForAttribute(context: Context, attr: Int): TextAppearanceSpan {
    if (context.theme.resolveAttribute(attr, typedValue, true)) {
        return TextAppearanceSpan(context, typedValue.resourceId)
    } else {
        throw IllegalArgumentException("TextAppearance theme attribute can not be resolved")
    }
}