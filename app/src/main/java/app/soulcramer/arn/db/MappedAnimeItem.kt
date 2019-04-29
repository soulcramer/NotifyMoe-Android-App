package app.soulcramer.arn.db

data class MappedAnimeItem(
    val userId: String,
    val animeId: String,
    val status: String,
    val episodes: Int,
    val animeName: String
)