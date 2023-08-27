package com.finance.news.app.data

import android.annotation.SuppressLint
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@ProvidedTypeConverter
object Converters {

    @SuppressLint("SimpleDateFormat")
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return if (value != null) {
            try {
                val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
                df.timeZone = timeZone
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun dateToTimestamp(value: Date?): String? {
        val timeZone: TimeZone = TimeZone.getTimeZone("UTC")
        df.timeZone = timeZone
        return if (value == null) null else df.format(value)
    }
}