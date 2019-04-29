package app.soulcramer.arn.db

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object NotifyMoeTypeConverters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?) = value?.let { formatter.parse(value, OffsetDateTime::from) }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? = date?.format(formatter)

    @TypeConverter
    @JvmStatic
    fun toInstant(value: Long?) = value?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    @JvmStatic
    fun fromInstant(date: Instant?) = date?.toEpochMilli()
}