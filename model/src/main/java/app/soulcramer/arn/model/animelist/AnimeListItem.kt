package app.soulcramer.arn.model.animelist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import app.soulcramer.arn.model.anime.Anime
import app.soulcramer.arn.model.user.User

@Entity(
    tableName = "anime_list_items",
    primaryKeys = ["user_id", "anime_id"],
    indices = [Index("user_id", "anime_id", unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Anime::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("anime_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
open class AnimeListItem(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "anime_id")
    val animeId: String,
    val status: String,
    val episodes: Int,
    val notes: String,
    val rewatch: Int,
    val created: String,
    val edited: String
)
