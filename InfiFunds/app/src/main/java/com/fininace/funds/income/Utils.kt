package com.fininace.funds.income

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.SimpleDateFormat
import java.util.Date

val Int.moneyFormat : String
    get() {
    val reversedStr = this.toString().reversed()
    val formattedStr = StringBuilder()

    for (i in reversedStr.indices step 3) {
        val chunk = reversedStr.substring(i, minOf(i + 3, reversedStr.length))
        formattedStr.append(chunk).append(".")
    }

    return formattedStr.reverse().drop(1).toString()
}

val ViewHolder.context: Context
    get() = itemView.context

fun calculateMonthsLeft(fromDate: Date): Long {
    val currentDate = Date()
    val timeDiffInMillis: Long = fromDate.time - currentDate.time
    return timeDiffInMillis / (30L * 24 * 60 * 60 * 1000)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date? {
    val format = SimpleDateFormat("MM/dd/yyyy")
    return format.parse(this)
}

val EditText.content: String
    get() = text.toString().trim()