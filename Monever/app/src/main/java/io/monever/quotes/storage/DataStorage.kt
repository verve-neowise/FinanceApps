package io.monever.quotes.storage

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import io.monever.quotes.models.Author
import io.monever.quotes.models.Quote

class QuoteList : ArrayList<Quote>()
class AuthorList : ArrayList<Author>()

private var quotes: QuoteList? = null
private var authors: AuthorList? = null

fun getDailyQuote(context: Context): Quote? {
    return loadQuotes(context)?.ifEmpty { null }?.random()
}

fun loadQuotes(context: Context): QuoteList? {
    if (quotes != null) {
        return quotes
    }
    return try {
        val json = context.assets.open("quotes.json").bufferedReader().readText()
        quotes = Gson().fromJson(json, QuoteList::class.java)
        quotes
    }
    catch (e: Exception) {
        Log.d("DATA", "loadQuotes: $e")
        null
    }
}

fun loadAuthors(context: Context): AuthorList? {
    return try {
        val json = context.assets.open("authors.json").bufferedReader().readText()
        authors = Gson().fromJson(json, AuthorList::class.java)
        authors
    }
    catch (e: Exception) {
        Log.d("DATA", "loadAuthors: $e")
        null
    }
}
