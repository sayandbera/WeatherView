package com.sayanx.weatherview.util

import java.text.SimpleDateFormat

object Utils {

    fun formateDate(timestamp: Int): String {
        val sdf = SimpleDateFormat("EEE, MMM d")
        val date = java.util.Date(timestamp.toLong() * 1000)
        return sdf.format(date)
    }
    fun formateDateTime(timestamp: Int): String {
        val sdf = SimpleDateFormat("hh:mm aa")
        val date = java.util.Date(timestamp.toLong() * 1000)
        return sdf.format(date)
    }
    fun formateDecimals(item: Double): String {
        return " %.0f".format(item)
    }

}
