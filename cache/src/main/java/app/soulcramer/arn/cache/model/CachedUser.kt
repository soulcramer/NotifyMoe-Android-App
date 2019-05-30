package app.soulcramer.arn.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model used solely for the caching of a user
 */
@Entity(tableName = "users")
data class CachedUser(
    @PrimaryKey
    val id: String,
    val name: String,
    val title: String,
    val avatar: String,
    val cover: String
)