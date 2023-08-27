package com.owe.track.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.owe.track.data.Debt
import com.owe.track.data.Payment
import com.owe.track.data.debtDatabase
import com.owe.track.nextPaymentDate
import kotlinx.coroutines.launch
import java.util.Date

class DebtViewModel(application: Application) : AndroidViewModel(application) {

    private val debtDatabase by debtDatabase()

    val debts = MutableLiveData<List<Debt>>()

    fun fetch() {
        viewModelScope.launch {
            debts.postValue(
                debtDatabase.debtRepository.allDebts()
            )
        }
    }

    fun create(debt: Debt) {
        viewModelScope.launch {
            debtDatabase.debtRepository.createDebt(debt)
            fetch()
        }
    }

    fun complete(debt: Debt) {
        viewModelScope.launch {
            debtDatabase.debtRepository.updateDebt(debt.copy(isCompleted = true))
            debtDatabase.debtRepository.createPayment(
                Payment(
                    target = debt.title,
                    amount = debt.amount,
                    date = Date(),
                    isComplete = true,
                    isOnce = debt.isOnce,
                    iteration = debt.repeats
                )
            )
            fetch()
        }
    }

    fun closeCurrent(debt: Debt) {
        viewModelScope.launch {


            val newDebt = if (debt.isOnce) {
                debt.copy(isCompleted = true)
            }
            else {
                val repeats = debt.repeats - 1
                debt.copy(
                    repeats = if (repeats < 1) 0 else repeats,
                    isCompleted = repeats == 0,
                    paymentDate = debt.nextPaymentDate()
                )
            }
            debtDatabase.debtRepository.updateDebt(newDebt)
            debtDatabase.debtRepository.createPayment(
                Payment(
                    target = debt.title,
                    date = Date(),
                    amount = debt.amount,
                    iteration = newDebt.repeats,
                    isOnce = newDebt.isOnce,
                    isComplete = debt.isCompleted
                )
            )
            fetch()
        }
    }
}