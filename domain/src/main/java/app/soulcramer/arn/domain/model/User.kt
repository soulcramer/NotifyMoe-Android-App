package app.soulcramer.arn.domain.model

/**
 * Representation for a [User] fetched from an external layer data source
 */
data class User(
    val id: String,
    val name: String,
    val title: String,
    val avatar: String,
    val cover: String
)