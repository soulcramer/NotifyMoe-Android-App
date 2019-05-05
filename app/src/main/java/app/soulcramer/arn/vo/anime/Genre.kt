package app.soulcramer.arn.vo.anime;

enum class Genre(val traktValue: String) {
    ACTION("action"),
    ADVENTURE("adventure"),
    CARS("cars"),
    COMEDY("comedy"),
    DRAMA("drama"),
    ECCHI("ecchi"),
    FANTASY("fantasy"),
    GAME("game"),
    HAREM("harem"),
    HISTORICAL("historical"),
    HORROR("horror"),
    KIDS("kids"),
    MAGIC("magic"),
    MARTIAL_ARTS("martial arts"),
    MECHA("mecha"),
    MILITARY("military"),
    MUSIC("music"),
    MYSTERY("mystery"),
    PSYCHOLOGICAL("psychological"),
    ROMANCE("romance"),
    SCHOOL("school"),
    SCIENCE_FICTION("sci-fi"),
    SEINEN("seinen"),
    SHOUJO("shoujo"),
    SHOUNEN("shounen"),
    SLICE_OF_LIFE("slice of life"),
    SPACE("space"),
    SPORTS("sports"),
    SUPER_POWER("super power"),
    SUPERNATURAL("supernatural"),
    THRILLER("thriller"),
    VAMPIRE("vampire");

    companion object {
        fun fromTraktValue(value: String): Genre? = values().firstOrNull { it.traktValue == value }
    }
}