package io.monever.quotes.ui.quotes

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.monever.quotes.models.Quote
import io.monever.quotes.storage.QuoteList
import io.monever.quotes.storage.getFavoriteQuotes
import io.monever.quotes.storage.loadQuotes
import io.monever.quotes.storage.setFavoriteQuote

class QuotesState(
    val quotes: List<Quote> = listOf(),
)

class QuotesViewModel(application: Application) : AndroidViewModel(application) {

    var state: QuotesState by mutableStateOf(QuotesState(listOf()))
        private set

    init {
        fetch()
    }

    private fun fetch() {
        state = QuotesState(
            quotes = loadQuotes(getApplication()) ?: listOf()
        )
    }

    fun setFavorite(id: Int, favorite: Boolean) {
        setFavoriteQuote(id, favorite, getApplication())
        state.quotes.find { it.id == id }?.isFavorite?.value = favorite
    }
}