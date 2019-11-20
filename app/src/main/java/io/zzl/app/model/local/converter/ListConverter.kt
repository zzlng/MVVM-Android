package io.zzl.app.model.local.converter

import androidx.room.TypeConverter

object ListConverter {

    @TypeConverter
    @JvmStatic
    fun listToString(list: List<String>?): String?
            = list?.let { it.reduce { acc, s -> "$acc,$s" } }

    @TypeConverter
    @JvmStatic
    fun listFromString(value: String?): List<String>?
            = value?.let { return it.split(",") }
}