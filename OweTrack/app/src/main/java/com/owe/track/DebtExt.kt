package com.owe.track

import android.content.Context
import com.owe.track.data.Debt
import com.owe.track.data.IntervalUnit
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

fun Debt.nextPaymentDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = paymentDate

    val field = when(intervalUnit) {
        IntervalUnit.Day -> Calendar.DAY_OF_MONTH
        IntervalUnit.Week -> Calendar.WEEK_OF_MONTH
        IntervalUnit.Month -> Calendar.MONTH
    }

    calendar.add(field, interval)

    return calendar.time
}

val Debt.leftDays: Int
    get() = TimeUnit.MILLISECONDS.toDays(paymentDate.time - Date().time).toInt()

val Debt.isToday: Boolean
    get() = leftDays == 0

fun Context.getInterval(debt: Debt): String {
    return getString(R.string.repeat_template, debt.interval, getString(debt.intervalUnit.textRes), debt.repeats)
}