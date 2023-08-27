package com.fininace.calculate.income.data.payment

import com.fininace.calculate.income.data.Data
import com.fininace.calculate.income.format

data class DifferentiatedPayment(
    val month: Int,
    val totalPayment: Float,
    val interestPayment: Float,
    val principalPayment: Float
): Data {
    override fun raw(): List<String> {
        return listOf(month.toString(), totalPayment.format(2), interestPayment.format(2), principalPayment.format(2))
    }
}

fun calculateDifferentiatedMortgageLoan(principal: Float, interestRate: Float, months: Int): List<Data> {
    val monthlyInterestRate = interestRate / 12 / 100
    val monthlyPayment = principal / months

    var remainingBalance = principal
    val paymentsList = mutableListOf<DifferentiatedPayment>()

    for (month in 1..months) {
        val interestPayment = remainingBalance * monthlyInterestRate
        val principalPayment = monthlyPayment - interestPayment
        remainingBalance -= principalPayment

        val totalPayment = monthlyPayment + interestPayment
        val monthlyPaymentInfo = DifferentiatedPayment(month, totalPayment, interestPayment, principalPayment)
        paymentsList.add(monthlyPaymentInfo)
    }

    return paymentsList
}
