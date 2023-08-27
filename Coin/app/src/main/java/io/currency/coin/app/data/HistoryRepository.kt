package io.currency.coin.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryRepository {

    @Insert
    suspend fun insert(history: History)

    @Query("select * from history")
    suspend fun getAll(): List<History>
}