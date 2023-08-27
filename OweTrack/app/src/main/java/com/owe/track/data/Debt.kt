package com.owe.track.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "debts")
data class Debt(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val amount: Int,
    val paymentDate: Date,
    val isOnce: Boolean = false,
    val date: Date? = null,
    val isInterval: Boolean = false,
    val repeats: Int = 0,
    val interval: Int = 0,
    val intervalUnit: IntervalUnit = IntervalUnit.Day,
    val comment: String = "",
    val isCompleted: Boolean = false
)