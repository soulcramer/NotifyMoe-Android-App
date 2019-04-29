package app.soulcramer.arn.vo.anime

import androidx.room.ColumnInfo
import androidx.room.Ignore

data class Title(
    var canonical: String,
    var romaji: String,
    var english: String,
    var japanese: String,
    var hiragana: String,
    @ColumnInfo(name = "synonyms") val _synonyms: String? = null
) {
    @delegate:Ignore
    val synonyms by lazy(LazyThreadSafetyMode.NONE) {
        _synonyms?.split(",")?.map { it.trim() } ?: emptyList()
    }
}
