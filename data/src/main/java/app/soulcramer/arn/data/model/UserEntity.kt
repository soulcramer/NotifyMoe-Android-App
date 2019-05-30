package app.soulcramer.arn.data.model

/**
 * Representation for a [UserEntity] fetched from an external layer data source
 */
data class UserEntity(val id: String, val nickname: String, val role: String, val avatar: String, val cover: String)