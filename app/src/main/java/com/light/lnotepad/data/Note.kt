package com.light.lnotepad.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.light.lnotepad.helper.DateFormatHelper
import java.io.Serializable
import java.util.*
import javax.inject.Inject

@Entity(tableName = "t_note")
class Note @Inject constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var tag: String,
    var title: String,
    var content: String,
    @ColumnInfo(name = "color")
    var color: Int,
    @ColumnInfo(name = "create_time")
    var createTime: Date,
    @ColumnInfo(name = "update_time")
    var updateTime: Date,
    @ColumnInfo(name = "start_time")
    var startTime: Date,
    @ColumnInfo(name = "end_time")
    var endTime: Date,
    @ColumnInfo(name = "order")
    var order: Long?
) : Serializable {
    override fun toString(): String = "$title: $color"

    override fun equals(other: Any?): Boolean {
        return id == (other as Note).id && color == other.color && tag == (other as Note).tag && title == other.title && content == other.content && createTime.equals(other.createTime) && endTime.equals(other.endTime)
    }
}