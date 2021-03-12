package com.light.lnotepad.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "t_note")
class Note constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var tag: String,
    var title: String,
    var content: String,
    @ColumnInfo(name = "theme_id")
    var themeId: Int,
    @ColumnInfo(name = "create_time")
    var createTime: Date,
    @ColumnInfo(name = "start_time")
    var startTime: Date,
    @ColumnInfo(name = "end_time")
    var endTime: Date,
) : Serializable {
    override fun toString(): String = title

    fun convertStartTimeStr():String {
        return SimpleDateFormat("yyyy-MM-dd").format(startTime)
    }

    fun convertEndTimeStr(): String {
        return SimpleDateFormat("yyyy-MM-dd").format(endTime)
    }

    fun convertCreateTimeStr(): String {
        return SimpleDateFormat("yyyy-MM-dd").format(createTime)
    }
}