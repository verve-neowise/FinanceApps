package io.currency.coin.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.currency.coin.app.data.History
import io.currency.coin.app.data.historyDatabase
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val historyDatabase by historyDatabase()

    val history = MutableLiveData<List<History>>(listOf())

    fun fetch() {
        viewModelScope.launch {
            val items = historyDatabase.historyRepository.getAll()
            history.postValue(items)
        }
    }
}