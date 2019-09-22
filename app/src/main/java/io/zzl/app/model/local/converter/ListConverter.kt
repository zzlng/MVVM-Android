package io.zzl.app.model.local.converter

import androidx.room.TypeConverter

object ListConverter {

    @TypeConverter
    @JvmStatic
    fun arrayToString(list: List<String>?): String?
            = list?.let { it.reduce { acc, s -> "$acc,$s" } }

    @TypeConverter
    @JvmStatic
    fun StringToArray(value: String?): List<String>?
            = value?.let { return it.split(",") }
}