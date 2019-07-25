package app.soulcramer.arn.domain.model.anime

import java.time.OffsetDateTime

/**
 * Representation for a [Anime] fetched from an external layer data source
 */
data class Anime(
    val id: String,
    val title: String,
    val synopsis: String,
    val startDate: OffsetDateTime?,
    val endDate: OffsetDateTime?,
    val status: Status,
    val source: Source,
    val type: Type,
    val episodeCount: Int?,
    val episodeLength: Int?,
    val genres: Set<Genre>,
    val hasCover: Boolean
) {

    val coverUrl: String
        get() {
            if (hasCover) return "https://media.notify.moe/images/anime/large/$id.webp"
            return "https://media.notify.moe/images/elements/default-cover.jpg"
        }
}