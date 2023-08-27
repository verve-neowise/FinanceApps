package com.fininace.calculate.income.data.payment

import com.fininace.calculate.income.data.Data
import com.fininace.calculate.income.format
import kotlin.math.pow

data class AnnuityPayment(
    val month: Int,
    val monthlyPayment: Float,
    val interestPayment: Float,
    val remainingBalance: Float
) : Data {

    override fun raw(): List<String> {
        return listOf(month.toString(), monthlyPayment.format(2), interestPayment.format(2), remainingBalance.format(2))
    }
}

fun calculateAnnuityMortgageLoan(principal: Float, interestRate: Float, months: Int): List<AnnuityPayment> {
    val monthlyInterestRate = interestRate / 12 / 100
    val annuityFactor = (monthlyInterestRate * (1 + monthlyInterestRate).pow(months)) / ((1 + monthlyInterestRate).pow(
        months.toFloat()
    ) - 1)

    val paymentsList = mutableListOf<AnnuityPayment>()

    var remainingBalance = principal

    for (month in 1..months) {
        val interestPayment = remainingBalance * monthlyInterestRate
        val monthlyPayment = principal * annuityFactor
        remainingBalance -= monthlyPayment - interestPayment

        val monthlyPaymentInfo = AnnuityPayment(month, monthlyPayment, interestPayment, remainingBalance)
        paymentsList.add(monthlyPaymentInfo)
    }

    return paymentsList
}
