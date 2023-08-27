package com.owe.track.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val target: String,
    val amount: Int,
    val date: Date,
    val iteration: Int,
    val isOnce: Boolean,
    val isComplete: Boolean
)