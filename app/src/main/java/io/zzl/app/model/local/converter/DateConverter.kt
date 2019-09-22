package io.zzl.app.model.local.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun dateFromLong(value: Date?): Long? = value?.time

    @TypeConverter
    @JvmStatic
    fun dateToLong(value: Long?): Date? = value?.let(::Date)
}