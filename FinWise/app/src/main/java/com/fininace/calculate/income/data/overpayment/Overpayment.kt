package com.fininace.calculate.income.data.overpayment

import com.fininace.calculate.income.data.Data
import kotlin.math.pow

data class Overpayment(
    val month: Int,
    val monthlyPayment: Float,
    val interestPayment: Float,
    val principalPayment: Float,
    val totalOverpayment: Float
) : Data {
    override fun raw(): List<String> {
        return listOf(
            month.toString(),
            monthlyPayment.toString(),
            interestPayment.toString(),
            principalPayment.toString(),
            totalOverpayment.toString()
        )
    }
}

fun calculateAnnuityOverpayments(principal: Float, interestRate: Float, months: Int): List<Overpayment> {
    val monthlyInterestRate = interestRate / 12 / 100
    val annuityFactor =
        (monthlyInterestRate * (1 + monthlyInterestRate).pow(months)) / ((1 + monthlyInterestRate).pow(months) - 1)

    val paymentsList = mutableListOf<Overpayment>()

    var remainingBalance = principal
    var totalOverpayment = 0.0f

    for (month in 1..months) {
        val interestPayment = remainingBalance * monthlyInterestRate
        val principalPayment = annuityFactor * principal - interestPayment
        val monthlyPayment = interestPayment + principalPayment
        totalOverpayment += monthlyPayment

        val paymentInfo = Overpayment(month, monthlyPayment, interestPayment, principalPayment, totalOverpayment)
        paymentsList.add(paymentInfo)
        remainingBalance -= principalPayment
    }

    return paymentsList
}

fun calculateDifferentiatedOverpayments(principal: Float, interestRate: Float, months: Int): List<Overpayment> {
    val monthlyInterestRate = interestRate / 12 / 100

    val paymentsList = mutableListOf<Overpayment>()

    var remainingBalance = principal
    var totalOverpayment = 0.0f

    for (month in 1..months) {
        val interestPayment = remainingBalance * monthlyInterestRate
        val principalPayment = principal / months
        val monthlyPayment = interestPayment + principalPayment
        totalOverpayment += monthlyPayment

        val paymentInfo = Overpayment(month, monthlyPayment, interestPayment, principalPayment, totalOverpayment)
        paymentsList.add(paymentInfo)
        remainingBalance -= principalPayment
    }

    return paymentsList
}