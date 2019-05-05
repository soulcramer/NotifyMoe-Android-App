package app.soulcramer.arn.ui.anime

import android.content.Context
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.emoji.text.EmojiCompat
import app.soulcramer.arn.R
import app.soulcramer.arn.ui.common.GenreStringer
import app.soulcramer.arn.ui.common.text.textAppearanceSpanForAttribute
import app.soulcramer.arn.vo.anime.Anime
import app.soulcramer.arn.vo.anime.Genre

class AnimeDetailsTextCreator(
    private val context: Context
) {
    fun showTitle(anime: Anime): CharSequence = buildSpannedString {
        inSpans(textAppearanceSpanForAttribute(context, R.attr.textAppearanceHeadline6)) {
            append(anime.title.canonical)
        }
        anime.type.also { type ->
            append(" ")
            inSpans(textAppearanceSpanForAttribute(context, R.attr.textAppearanceCaption)) {
                append("(")
                append(type.toUpperCase())
                append(")")
            }
        }
    }

    fun genreString(genres: List<Genre>?): CharSequence? {
        if (genres != null && genres.isNotEmpty()) {
            val spanned = buildSpannedString {
                for (i in 0 until genres.size) {
                    val genre = genres[i]
                    inSpans(textAppearanceSpanForAttribute(context, R.attr.textAppearanceCaption)) {
                        append(context.getString(GenreStringer.getLabel(genre)))
                    }
                    append("\u00A0") // nbsp
                    append(GenreStringer.getEmoji(genre))
                    if (i < genres.size - 1) append(" \u2022 ")
                }
            }

            val emojiCompat = EmojiCompat.get()
            return when {
                emojiCompat.loadState == EmojiCompat.LOAD_STATE_SUCCEEDED -> emojiCompat.process(spanned)
                else -> spanned
            }
        }
        return null
    }

    fun genreContentDescription(genres: List<Genre>?): CharSequence? {
        return genres?.joinToString(", ") { context.getString(GenreStringer.getLabel(it)) }
    }
}