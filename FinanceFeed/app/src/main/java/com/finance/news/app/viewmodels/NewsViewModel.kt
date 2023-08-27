package com.finance.news.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.finance.news.app.data.Ticket
import com.finance.news.app.data.favoritesDatabase
import com.finance.news.app.data.newsApi
import com.finance.news.app.mapFavorites
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesDatabase by favoritesDatabase()
    private val newsApi by newsApi()

    val news = MutableLiveData<List<Ticket>>(listOf())
    val favorites = MutableLiveData<List<Ticket>>(listOf())

    fun fetch() {
        viewModelScope.launch {
            try {
                val locals = favoritesDatabase.favoritesRepository.findAll()
                val mapped = newsApi.getNews().results.mapFavorites(locals)
                favorites.postValue(locals)
                news.postValue(mapped)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addFavorite(ticket: Ticket) {
        viewModelScope.launch {
            favoritesDatabase.favoritesRepository.add(ticket)
        }
    }

    fun removeFavorite(ticket: Ticket) {
        viewModelScope.launch {
            favoritesDatabase.favoritesRepository.delete(ticket)
            favorites.postValue(favorites.value?.filter { it.id != ticket.id })
        }
    }
}