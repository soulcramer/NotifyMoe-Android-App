package app.soulcramer.arn.vo.anime

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class Anime(
    @PrimaryKey val id: String,
    val type: String,
    @Embedded val title: Title,
    val summary: String,
    val status: String,
    @ColumnInfo(name = "genres") val _genres: String? = null,
    val startDate: String,
    val endDate: String,
    val episodeCount: Int,
    val episodeLength: Int,
    val source: String,
    @Embedded val image: Image,
    @Embedded val rating: Rating,
    val notes: String?,
    val rewatch: Int = 0,
    val createdBy: String,
    val editedBy: String
) {
    @delegate:Ignore
    val genres: List<Genre> by lazy(LazyThreadSafetyMode.NONE) {
        _genres?.split(",")?.mapNotNull { Genre.fromTraktValue(it.trim()) } ?: emptyList()
    }
}
