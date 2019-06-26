package app.soulcramer.arn.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

/**
 * Model used solely for the caching of a user
 */
@Entity(tableName = "users")
data class CachedUser(
    @PrimaryKey
    val id: String,
    val nickname: String,
    val role: String,
    val proExpiresDate: OffsetDateTime?,
    val hasAvatar: Boolean,
    val hasCover: Boolean
)