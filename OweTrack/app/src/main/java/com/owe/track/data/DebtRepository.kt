package com.owe.track.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DebtRepository {

    @Insert
    suspend fun createDebt(vararg debts: Debt)

    @Query("select * from debts order by paymentDate asc")
    suspend fun allDebts(): List<Debt>

    @Update
    suspend fun updateDebt(debt: Debt)

    @Insert
    suspend fun createPayment(vararg payment: Payment)

    @Query("select * from payments order by date desc")
    suspend fun allPayments(): List<Payment>
}