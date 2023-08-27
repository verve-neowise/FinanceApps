package io.monever.quotes.ui.authors

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.monever.quotes.models.Author
import io.monever.quotes.models.Quote
import io.monever.quotes.storage.getFavoriteQuotes
import io.monever.quotes.storage.loadAuthors
import io.monever.quotes.storage.setFavoriteQuote

data class AuthorsState(
    val authors: List<Author> = listOf()
)

class AuthorsViewModel(application: Application) : AndroidViewModel(application) {

    var state: AuthorsState by mutableStateOf(AuthorsState())
        private set

    init {
        fetch()
    }

    fun fetch() {
        state = AuthorsState(
            authors = mapFavorite(loadAuthors(getApplication()) ?: listOf())
        )
    }

    private fun mapFavorite(quotes: List<Author>): List<Author> {
        val favorites = getFavoriteQuotes(getApplication())
        return quotes.map { it.copy(isFavorite = mutableStateOf(favorites.contains(it.id.toString()))) }
    }

    fun setFavorite(id: Int, favorite: Boolean) {
        setFavoriteQuote(id, favorite, getApplication())
        state.authors.find { it.id == id }?.isFavorite?.value = favorite
    }
}