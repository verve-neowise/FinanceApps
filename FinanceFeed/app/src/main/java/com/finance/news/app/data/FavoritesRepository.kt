package com.finance.news.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesRepository {

    @Query("select * from ticket")
    suspend fun findAll(): List<Ticket>

    @Insert
    suspend fun add(ticket: Ticket): Long

    @Delete
    suspend fun delete(ticket: Ticket)
}