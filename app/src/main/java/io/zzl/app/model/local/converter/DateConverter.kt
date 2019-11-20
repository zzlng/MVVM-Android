package io.zzl.app.model.local.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun dateToLong(value: Date?): Long? = value?.time

    @TypeConverter
    @JvmStatic
    fun dateFromLong(value: Long?): Date? = value?.let(::Date)

    @TypeConverter
    @JvmStatic
    @Suppress("NewAPI")
    fun instantToLong(value: Instant?): Long? = value?.epochSecond

    @TypeConverter
    @JvmStatic
    @Suppress("NewAPI")
    fun instantFromLong(value: Long?): Instant? = value?.let { Instant.ofEpochSecond(it) }
}