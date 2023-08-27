package com.owe.track

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.owe.track.data.IntervalUnit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
private val format = SimpleDateFormat("dd/MM/yyyy")

fun nextPaymentDate(
    current: Date,
    interval: Int,
    intervalUnit: IntervalUnit
): Date {
    val calendar = Calendar.getInstance()
    calendar.time = current

    val field = when(intervalUnit) {
        IntervalUnit.Day -> Calendar.DAY_OF_MONTH
        IntervalUnit.Week -> Calendar.WEEK_OF_MONTH
        IntervalUnit.Month -> Calendar.MONTH
    }

    calendar.add(field, interval)

    return calendar.time
}

fun leftDays(date: Date): Int {
    return TimeUnit.MILLISECONDS.toDays(date.time - Date().time).toInt()
}

val Int.moneyFormat : String
    get() {
    val reversedStr = this.toString().reversed()
    val formattedStr = StringBuilder()

    for (i in reversedStr.indices step 3) {
        val chunk = reversedStr.substring(i, minOf(i + 3, reversedStr.length))
        formattedStr.append(chunk).append(",")
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

fun Date.format(): String {
    return format.format(this)
}

fun String.toDate(): Date? {
    return try { format.parse(this) } catch (e: Exception) { null }
}

val EditText.content: String
    get() = text.toString().trim()

fun String.safeToInt() = try { this.toInt() } catch (e: Exception) { 0 }

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes messageRes: Int) {
    Toast.makeText(requireContext(), getString(messageRes), Toast.LENGTH_SHORT).show()
}

fun onEmptyFields(vararg conditions: Boolean, action: () -> Unit): Boolean {
    val result = conditions.any { it }
    if (result) action()
    return result
}