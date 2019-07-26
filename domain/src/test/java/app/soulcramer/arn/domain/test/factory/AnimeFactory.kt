package app.soulcramer.arn.domain.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomBoolean
import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.domain.model.anime.Anime
import app.soulcramer.arn.domain.model.anime.Genre
import app.soulcramer.arn.domain.model.anime.Source
import app.soulcramer.arn.domain.model.anime.Status
import app.soulcramer.arn.domain.model.anime.Type
import java.time.OffsetDateTime
import kotlin.random.Random

/**
 * Factory class for User related instances
 */
class AnimeFactory {

    companion object Factory {

        fun makeAnime(
            id: String = randomUuid(),
            title: String = randomUuid(),
            synopsis: String = randomUuid(),
            startDate: OffsetDateTime = OffsetDateTime.now(),
            endDate: OffsetDateTime = OffsetDateTime.now(),
            status: Status = randomStatus(),
            source: Source = randomSource(),
            type: Type = randomType(),
            episodeCount: Int? = null,
            episodeLength: Int? = null,
            genres: Set<Genre> = randomSetGenres(),
            hasCover: Boolean = randomBoolean()
        ): Anime {
            return Anime(
                id,
                title,
                synopsis,
                startDate,
                endDate,
                status,
                source,
                type,
                episodeCount,
                episodeLength,
                genres,
                hasCover
            )
        }

        fun makeAnimeList(
            count: Int,
            title: String = "",
            hasCover: Boolean = true
        ): List<Anime> {
            val users = mutableListOf<Anime>()
            repeat(count) {
                users.add(makeAnime(title = title, hasCover = hasCover))
            }
            return users
        }

        fun randomStatus(): Status {
            return Status.values().toList().shuffled().first()
        }

        fun randomSource(): Source {
            return Source.values().toList().shuffled().first()
        }

        fun randomType(): Type {
            return Type.values().toList().shuffled().first()
        }

        fun randomGenre(): Genre {
            return Genre.values().toList().shuffled().first()
        }

        fun randomSetGenres(): Set<Genre> {
            val genres = Genre.values().toList()
            val genreCount = genres.size
            return genres.shuffled()
                .distinct()
                .take(Random.nextInt(1, genreCount))
                .toSet()
        }
    }
}