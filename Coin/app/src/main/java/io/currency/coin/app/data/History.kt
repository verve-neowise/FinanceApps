package io.currency.coin.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: Date = Date(),
    val fromValue: Double = 0.0,
    val fromCurrency: String = "USD",
    val toValue: Double = 0.0,
    val toCurrency: String = "USD",
)