package com.fininace.funds.income.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalRepository {

    @Query("select * from goals")
    fun findAll(): Flow<List<Goal>>

    @Insert
    suspend fun add(goal: Goal): Long

    @Update
    suspend fun update(goal: Goal): Int

    @Delete
    suspend fun delete(goal: Goal): Unit
}