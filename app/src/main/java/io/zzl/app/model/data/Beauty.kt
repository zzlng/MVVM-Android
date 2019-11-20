package io.zzl.app.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant

@Entity(tableName = "beauties")
data class Beauty(

        @PrimaryKey
        @SerializedName("_id")
        @ColumnInfo(name = "beautyid")
        var id: String, // 5ccdbc219d212239df927a93

        @SerializedName("createdAt")
        @ColumnInfo(name = "create")
        var createdAt: String, // 2019-05-04T16:21:53.523Z

        @SerializedName("desc")
        var desc: String, // 2019-05-05

        @SerializedName("publishedAt")
        @ColumnInfo(name = "publish")
        var publishedAt: String, // 2019-05-04T16:21:59.733Z

        @SerializedName("source")
        var source: String, // web

        @SerializedName("type")
        var type: String, // 福利

        @SerializedName("url")
        var url: String, // http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg

        @SerializedName("used")
        var used: Boolean, // true

        @SerializedName("who")
        var who: String, // lijinshanmx

        @ColumnInfo(name = "creation_date")
        override var creationDate: Instant,

        @ColumnInfo(name = "modification_date")
        override var modificationDate: Instant
) : BaseModel
