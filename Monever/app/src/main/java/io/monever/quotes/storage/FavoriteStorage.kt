package io.monever.quotes.storage

import android.annotation.SuppressLint
import android.content.Context

fun getFavoriteQuotes(context: Context): MutableSet<String> {
    val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    return prefs.getStringSet("quotes", null) ?: mutableSetOf()
}

fun getFavoriteAuthors(context: Context): MutableSet<String> {
    val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    return prefs.getStringSet("authors", null) ?: mutableSetOf()
}

@SuppressLint("MutatingSharedPrefs")
fun setFavoriteQuote(id: Int, isSelected: Boolean, context: Context) {

    val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    val favs = prefs.getStringSet("quotes", null) ?: mutableSetOf()

    if (isSelected) {
        favs.remove(id.toString())
    }
    else {
        favs.add(id.toString())
    }

    prefs.edit().putStringSet("quotes", favs).apply()
}

@SuppressLint("MutatingSharedPrefs")
fun setFavoriteAuthor(id: Int, isSelected: Boolean, context: Context) {

    val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    val favs = prefs.getStringSet("authors", null) ?: mutableSetOf()

    if (isSelected) {
        favs.remove(id.toString())
    }
    else {
        favs.add(id.toString())
    }

    prefs.edit().putStringSet("quotes", favs).apply()
}