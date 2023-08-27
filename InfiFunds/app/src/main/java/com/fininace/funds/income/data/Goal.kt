package com.fininace.funds.income.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val endDate: Date,
    val total: Int,
    val current: Int,
    val type: GoalType
)