package com.light.lnotepad.helper

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

object DateFormatHelper  {
    val formater1 = SimpleDateFormat("yyyy-MM-dd")
    fun convertDate2(format: String, date: Date): String {
        return when (format) {
            "yyyy-MM-dd" -> {
                formater1.format(date)
            }
            else -> {
                formater1.format(date)
            }
        }
    }
}