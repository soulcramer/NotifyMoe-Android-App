package app.soulcramer.arn.vo.anime

import androidx.room.Embedded

data class Image(
    val extension: String,
    val width: String,
    val height: String,
    @Embedded
    val averageColor: AverageColor,
    val lastModified: String
)
