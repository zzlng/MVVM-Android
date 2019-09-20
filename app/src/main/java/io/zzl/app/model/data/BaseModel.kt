package io.zzl.app.model.data

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.Instant

interface BaseModel : Serializable {

    @set:[SerializedName(value = "creation_date") ColumnInfo(name = "creation_date")]
    @get:[SerializedName(value = "creation_date") ColumnInfo(name = "creation_date")]
    var creationDate: Instant

    @set:[SerializedName(value = "modification_date") ColumnInfo(name = "modification_date")]
    @get:[SerializedName(value = "modification_date") ColumnInfo(name = "modification_date")]
    var modificationDate: Instant
}