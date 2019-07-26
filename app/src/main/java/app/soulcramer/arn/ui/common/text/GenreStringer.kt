package app.soulcramer.arn.ui.common.text

import androidx.annotation.StringRes
import app.soulcramer.arn.R
import app.soulcramer.arn.domain.model.anime.Genre

@Suppress("LongMethod", "ComplexMethod")
object GenreStringer {
    fun getEmoji(genre: Genre): String = when (genre) {
        Genre.DRAMA -> "\uD83D\uDE28"
        Genre.FANTASY -> "\uD83E\uDDD9"
        Genre.SCIENCE_FICTION -> "\uD83D\uDE80️"
        Genre.ACTION -> "\uD83E\uDD20"
        Genre.ADVENTURE -> "\uD83C\uDFDE️"
        Genre.THRILLER -> "\uD83D\uDDE1️"
        Genre.COMEDY -> "\uD83E\uDD23"
        Genre.HORROR -> "\uD83E\uDDDF"
        Genre.MYSTERY -> "\uD83D\uDD75️"
        Genre.CARS -> "\uD83D\uDE97"
        Genre.ECCHI -> "\uD83D\uDC8B"
        Genre.GAME -> "\uD83C\uDFAE"
        Genre.HAREM -> "\uD83D\uDC65"
        Genre.HISTORICAL -> "⛩"
        Genre.KIDS -> "\uD83D\uDC76"
        Genre.MAGIC -> "\uD83E\uDDD9\u200D♀️"
        Genre.MARTIAL_ARTS -> "\uD83E\uDD3C\u200D♂️"
        Genre.MECHA -> "\uD83E\uDD16"
        Genre.MILITARY -> "\uD83D\uDCA3"
        Genre.MUSIC -> "\uD83C\uDFB8"
        Genre.PSYCHOLOGICAL -> "\uD83E\uDD14"
        Genre.ROMANCE -> "\uD83E\uDD70"
        Genre.SCHOOL -> "\uD83D\uDC69\u200D\uD83C\uDF93"
        Genre.SEINEN -> "\uD83E\uDDD1"
        Genre.SHOUJO -> "\uD83D\uDC67"
        Genre.SHOUNEN -> "\uD83D\uDC66"
        Genre.SLICE_OF_LIFE -> "\uD83D\uDEB6"
        Genre.SPACE -> "\uD83D\uDC69\u200D\uD83D\uDE80"
        Genre.SPORTS -> "\uD83C\uDFC0"
        Genre.SUPER_POWER -> "\uD83E\uDDB8\u200D♂️"
        Genre.SUPERNATURAL -> "\uD83D\uDD2E"
        Genre.VAMPIRE -> "\uD83E\uDDDB\u200D♂️"
    }

    @StringRes
    fun getLabel(genre: Genre): Int = when (genre) {
        Genre.DRAMA -> R.string.genre_label_drama
        Genre.FANTASY -> R.string.genre_label_fantasy
        Genre.SCIENCE_FICTION -> R.string.genre_label_science_fiction
        Genre.ACTION -> R.string.genre_label_action
        Genre.ADVENTURE -> R.string.genre_label_adventure
        Genre.THRILLER -> R.string.genre_label_thriller
        Genre.COMEDY -> R.string.genre_label_comedy
        Genre.HORROR -> R.string.genre_label_horror
        Genre.MYSTERY -> R.string.genre_label_mystery
        Genre.CARS -> R.string.genre_label_cars
        Genre.ECCHI -> R.string.genre_label_ecchi
        Genre.GAME -> R.string.genre_label_game
        Genre.HAREM -> R.string.genre_label_harem
        Genre.HISTORICAL -> R.string.genre_label_historical
        Genre.KIDS -> R.string.genre_label_kids
        Genre.MAGIC -> R.string.genre_label_magic
        Genre.MARTIAL_ARTS -> R.string.genre_label_martial_arts
        Genre.MECHA -> R.string.genre_label_mecha
        Genre.MILITARY -> R.string.genre_label_military
        Genre.MUSIC -> R.string.genre_label_music
        Genre.PSYCHOLOGICAL -> R.string.genre_label_psychological
        Genre.ROMANCE -> R.string.genre_label_romance
        Genre.SCHOOL -> R.string.genre_label_school
        Genre.SEINEN -> R.string.genre_label_seinen
        Genre.SHOUJO -> R.string.genre_label_shoujo
        Genre.SHOUNEN -> R.string.genre_label_shounen
        Genre.SLICE_OF_LIFE -> R.string.genre_label_slice_of_life
        Genre.SPACE -> R.string.genre_label_space
        Genre.SPORTS -> R.string.genre_label_sports
        Genre.SUPER_POWER -> R.string.genre_label_super_power
        Genre.SUPERNATURAL -> R.string.genre_label_supernatural
        Genre.VAMPIRE -> R.string.genre_label_vampire
    }
}