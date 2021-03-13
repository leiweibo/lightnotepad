package com.light.lnotepad.helper

import android.graphics.Color
import java.util.*

object ColorPool {
    val COURSE_BACKGROUND_COLOR = arrayOf(
        "#FAEBD7",
        "#FFE4C4",
        "#D1EEEE",
        "#FFEC8B",
        "#B3EE3A",
        "#7FFF00",
        "#FFD700",
        "#FF6A6A",
        "#FFA500",
        "#EE82EE",
        "#FF4040",
        "#D8BFD8",
        "#BFEFFF"
    )
    val LIGHT_COLOUR = arrayOf(
        "#FFFFCC", "#CCFFFF", "#FF9966",
        "#FF6666", "#FFCCCC", "#FFCC99", "#CCFF99", "#CCFFCC", "#CCCC99", "#0099FF",
        "#F5F5F5", "#FF9933", "#FF99CC", "#FF6600"
    )
    val LIGHT_COLOUR_INT = intArrayOf(
        Color.parseColor("#FFFFCC"), Color.parseColor("#CCFFFF"),
        Color.parseColor("#FF9966"), Color.parseColor("#FF6666"),
        Color.parseColor("#FFCCCC"), Color.parseColor("#FFCC99"),
        Color.parseColor("#CCFF99"), Color.parseColor("#CCFFCC"),
        Color.parseColor("#CCCC99"), Color.parseColor("#0099FF"),
        Color.parseColor("#F5F5F5"), Color.parseColor("#FF9933"),
        Color.parseColor("#FF99CC"), Color.parseColor("#FF6600")
    )

    val color: String
        get() {
            val date = Date()
            val random = Random(date.time)
            return LIGHT_COLOUR[random.nextInt(LIGHT_COLOUR.size)]
        }

    fun convertToARGB(color: Int): String {
        val red = Integer.toHexString(Color.red(color))
        val green =
            Integer.toHexString(Color.green(color))
        val blue = Integer.toHexString(Color.blue(color))
        return "#$red$green$blue"
    }
}