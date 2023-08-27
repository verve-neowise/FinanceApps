package com.owe.track.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.owe.track.data.Payment
import com.owe.track.data.debtDatabase
import kotlinx.coroutines.launch

class PaymentsHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val database by debtDatabase()

    val payments: MutableLiveData<List<Payment>> = MutableLiveData(listOf())

    fun fetch() {
        viewModelScope.launch {
            try {
                val list = database.debtRepository.allPayments()
                payments.postValue(list)
            }
            catch (e: Exception) {
                Toast.makeText(getApplication(), "Error while load data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}