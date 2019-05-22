package app.soulcramer.arn.data.model

/**
 * Representation for a [UserEntity] fetched from an external layer data source
 */
data class UserEntity(val id: String, val name: String, val title: String, val avatar: String, val cover: String)