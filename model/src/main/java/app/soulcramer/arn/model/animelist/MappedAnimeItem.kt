package app.soulcramer.arn.model.animelist

data class MappedAnimeItem(
    val userId: String,
    val animeId: String,
    val status: String,
    val episodes: Int,
    val animeName: String
)