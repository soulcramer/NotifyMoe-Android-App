package app.soulcramer.arn.remote.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class IsoDurationJsonAdapter {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun fromJson(value: String?): OffsetDateTime? {
        return value?.let { formatter.parse(value, OffsetDateTime::from) }
    }

    @ToJson
    fun toJson(date: OffsetDateTime): String? {
        return date.format(formatter)
    }
}