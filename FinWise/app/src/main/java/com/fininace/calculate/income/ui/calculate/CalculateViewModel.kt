package com.fininace.calculate.income.ui.calculate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculateViewModel : ViewModel() {
    val amount: MutableLiveData<String> = MutableLiveData("0")
    val duration: MutableLiveData<String> = MutableLiveData("0")
    val percent: MutableLiveData<String> = MutableLiveData("0")
}