package com.fininace.calculate.income

import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

fun seekbarListener(callback: (progress: Int) -> Unit): OnSeekBarChangeListener {

    return object : OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) callback(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }
}

fun onTextChanged(callback: (value: String) -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
}

fun formatCurrency(number: Int): String {
    val numberString = number.toString()
    val stringBuilder = StringBuilder()

    var count = 0
    for (i in numberString.length - 1 downTo 0) {
        if (count == 3) {
            stringBuilder.append(' ')
            count = 0
        }
        stringBuilder.append(numberString[i])
        count++
    }

    return stringBuilder.reverse().toString()
}

fun String.safeToInt(): Int {
    return toIntOrNull() ?: 0
}

fun String.safeToFloat(): Float {
    return toFloatOrNull() ?: 0f
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)
