package io.currency.coin.app.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.currency.coin.app.data.History
import io.currency.coin.app.data.Rate
import io.currency.coin.app.data.Symbol
import io.currency.coin.app.data.currenciesApi
import io.currency.coin.app.data.historyDatabase
import kotlinx.coroutines.launch
import java.util.Date

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private val currenciesApi by currenciesApi()
    private val historyDatabase by historyDatabase()

    val fromValue = MutableLiveData<Double>()
    val toValue = MutableLiveData<Double>()
    val fromCurrency = MutableLiveData<String>()
    val toCurrency = MutableLiveData<String>()

    val symbols = MutableLiveData<List<Symbol>>(listOf())
    val exchangeRates = MutableLiveData<List<Rate>>(listOf())

    fun fetchExchangeRates() {
        viewModelScope.launch {
            try {
                val result = currenciesApi.getLatest("")
                exchangeRates.postValue(
                    result
                        .rates
                        .map { it.copy(name = findSymbolName(it.code)) }
                        .sortedBy { it.name.ifEmpty { it.code } }
                )
            }
            catch (e: Exception) {
                Toast.makeText(getApplication(), "Network error, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findSymbolName(code: String): String {
        return symbols.value?.find { it.code == code }?.name ?: ""
    }

    fun fetch() {
        viewModelScope.launch {
            try {
                val result = currenciesApi.getSymbols().symbols()
                symbols.postValue(result)
            }
            catch (e: Exception) {
                Toast.makeText(getApplication(), "Network error, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun convert() {

        val amount = fromValue.value
        val from = fromCurrency.value
        val to = toCurrency.value

        if (from.isNullOrBlank() || to.isNullOrBlank()) {
            return Toast.makeText(getApplication(), "Select currencies", Toast.LENGTH_SHORT).show()
        }

        viewModelScope.launch {
           try {
               val rates = currenciesApi.getLatest("${from.uppercase()},${to.uppercase()}").rates
               val fromRate = rates.find { it.code == from.uppercase() }
               val toRate = rates.find { it.code == to.uppercase() }

               val converted = if (fromRate != null && toRate != null) {
                   val baseAmount = (amount ?: 0.0) / fromRate.rate
                    baseAmount * toRate.rate
               }
               else {
                   0.0
               }

               toValue.postValue(converted)

               historyDatabase.historyRepository.insert(History(
                   date = Date(),
                   fromValue = amount ?: 0.0,
                   fromCurrency = from,
                   toValue = converted,
                   toCurrency = to
               ))
           }
           catch (e: Exception) {
               Toast.makeText(getApplication(), "Network error, try again", Toast.LENGTH_SHORT).show()
           }
        }
    }

    fun setCurrencies(from: String, to: String) {
        fromCurrency.postValue(from)
        toCurrency.postValue(to)
    }

    fun setAmounts(fromAmount: Double, toAmount: Double) {
        fromValue.postValue(fromAmount)
        toValue.postValue(toAmount)
    }

    fun swap() {
        swapCurrencies()
        swapAmounts()
    }

    private fun swapCurrencies() {
        val temp = fromCurrency.value
        fromCurrency.postValue(toCurrency.value)
        toCurrency.postValue(temp ?: "")
    }

    private fun swapAmounts() {
        val temp = fromValue.value
        fromValue.postValue(toValue.value)
        toValue.postValue(temp ?: .0)
    }
}