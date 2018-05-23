package studio.lunabee.arn.vo

import android.arch.persistence.room.Entity
import io.realm.annotations.Index
import io.realm.annotations.Required

@Entity(primaryKeys = ["userId", "animeId"])
data class AnimelistItem(
    @field:Required
    val userId: String,
    @field:Required
    val animeId: String,
    @field:Index
    val status: String?,
    val episodes: Int?,
    val rating: Rating?,
    val notes: String?,
    val rewatch: Int?,
    val created: String?,
    val edited: String?
)