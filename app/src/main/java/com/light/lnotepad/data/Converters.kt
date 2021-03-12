package com.light.lnotepad.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Date): Long = calendar.time

    @TypeConverter
    fun datestampToCalendar(value: Long): Date =
        Calendar.getInstance().apply { timeInMillis = value }.time
}
