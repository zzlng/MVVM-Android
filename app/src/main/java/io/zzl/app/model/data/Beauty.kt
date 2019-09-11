package io.zzl.app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "beauties")
data class Beauty(

        @PrimaryKey
        @SerializedName("_id")
        @ColumnInfo(name = "beautyid")
        val id: String, // 5ccdbc219d212239df927a93

        @SerializedName("createdAt")
        @ColumnInfo(name = "create")
        val createdAt: String, // 2019-05-04T16:21:53.523Z

        @SerializedName("desc")
        val desc: Date, // 2019-05-05

        @SerializedName("publishedAt")
        @ColumnInfo(name = "publish")
        val publishedAt: String, // 2019-05-04T16:21:59.733Z

        @SerializedName("source")
        val source: String, // web

        @SerializedName("type")
        val type: String, // 福利

        @SerializedName("url")
        val url: String, // http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg

        @SerializedName("used")
        val used: Boolean, // true

        @SerializedName("who")
        val who: String // lijinshanmx
)