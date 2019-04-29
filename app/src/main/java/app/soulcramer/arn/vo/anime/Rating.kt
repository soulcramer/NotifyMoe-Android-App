package app.soulcramer.arn.vo.anime

import androidx.room.Embedded

data class Rating(
    val overall: Double,
    val story: Double,
    val visuals: Double,
    val soundtrack: Double,
    @Embedded(prefix = "count_")
    val count: Count
)
