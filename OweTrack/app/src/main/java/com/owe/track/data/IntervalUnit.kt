package com.owe.track.data

import androidx.annotation.StringRes
import com.owe.track.R

enum class IntervalUnit(
    @StringRes val textRes: Int
) {

    Day(R.string.day),
    Week(R.string.week),
    Month(R.string.month);
}