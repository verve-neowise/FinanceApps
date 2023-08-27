package com.owe.track.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

enum class CreateMode {
    Once,
    Interval
}

class CreateViewModel(application: Application) : AndroidViewModel(application) {

    val mode = MutableLiveData(CreateMode.Once)
}