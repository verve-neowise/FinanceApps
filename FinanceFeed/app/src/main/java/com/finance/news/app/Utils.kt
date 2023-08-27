package com.finance.news.app

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.finance.news.app.data.Ticket
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

val ViewHolder.context: Context
    get() = itemView.context

@SuppressLint("SimpleDateFormat")
private val utcDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

@SuppressLint("SimpleDateFormat")
private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date? {
    return dateFormat.parse(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.format(): String {
    return dateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.fromUtc(): Date? {
    return utcDateFormat.parse(this)
}

fun List<Ticket>.mapFavorites(favorites: List<Ticket>): List<Ticket> {
    return map { ticket ->
        ticket.apply {
            isFavorite = favorites.find { it.id == ticket.id } != null
        }
    }
}

fun Date.formatRelativeDate(): String {
    val now = Calendar.getInstance().time
    val diffInMillis = now.time - this.time

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

    return when {
        days > 30 -> format()
        days >= 7 -> "${days / 7} недел${if (days / 7 > 1) "и" else "ю"}"
        days >= 1 -> "$days ${if (days > 1) "дней" else "день"}"
        hours >= 1 -> "$hours ${if (hours > 1) "часов" else "час"}"
        minutes >= 1 -> "$minutes ${if (minutes > 1) "минут" else "минуту"}"
        else -> "Только что"
    }
}