package app.soulcramer.arn.data.model

import java.time.OffsetDateTime

/**
 * Representation for a [UserEntity] fetched from an external layer data source
 */
data class UserEntity(
    val id: String,
    val nickname: String,
    val role: String,
    val proExpiresDate: OffsetDateTime?,
    val hasAvatar: Boolean,
    val hasCover: Boolean
)