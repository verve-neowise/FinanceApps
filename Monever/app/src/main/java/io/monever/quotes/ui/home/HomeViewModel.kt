package io.monever.quotes.ui.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import io.monever.quotes.models.Quote
import io.monever.quotes.storage.getDailyQuote

data class HomeState(
    val dailyQuote: Quote? = null
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var state: HomeState by mutableStateOf(HomeState())
        private set

    init {
        dailyQuote()
    }

    private fun dailyQuote() {
        state = HomeState(
            dailyQuote = getDailyQuote(getApplication())
        )
    }
}