package io.zzl.app.model.local

import androidx.room.TypeConverter
import java.util.*

/*
    Singleton that contains all `@TypeConverter`s of the app.
    Mark all inner converters with `@JvmStatic` so Room can use them as regular static functions
*/
object Converters {

    @TypeConverter
    @JvmStatic
    fun convertDateFrom(value: Date?): Long? = value?.time

    @TypeConverter
    @JvmStatic
    fun convertDateTo(value: Long?): Date? = value?.let(::Date)

    @TypeConverter
    @JvmStatic
    fun arrayToString(array: Array<String>): String? = array?.let { it.reduce { acc, s ->  acc + "," + s } }

    @TypeConverter
    @JvmStatic
    fun StringToArray(value: String): Array<String>? = value?.let { return it.split(",").toTypedArray() }

}