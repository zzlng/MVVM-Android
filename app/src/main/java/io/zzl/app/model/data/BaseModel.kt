package io.zzl.app.model.data

import androidx.room.ColumnInfo
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import io.zzl.app.model.local.converter.DateConverter
import java.io.Serializable
import java.util.*

@TypeConverters(DateConverter::class)
interface BaseModel : Serializable {

    @set:[SerializedName(value = "creation_date") ColumnInfo(name = "creation_date")]
    @get:[SerializedName(value = "creation_date") ColumnInfo(name = "creation_date")]
    var creationDate: Date

    @set:[SerializedName(value = "modification_date") ColumnInfo(name = "modification_date")]
    @get:[SerializedName(value = "modification_date") ColumnInfo(name = "modification_date")]
    var modificationDate: Date
}