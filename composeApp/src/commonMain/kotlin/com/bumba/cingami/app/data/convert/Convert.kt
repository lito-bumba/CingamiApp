package com.bumba.cingami.app.data.convert

object Convert {

    fun convertCurrency(rate: Double, amount: Double): Double {
        return amount * rate
    }

    fun calculateFee(value: Double, percentage: Double): Double {
        return value * (percentage / 100)
    }

    fun calculateTotal(amount: Double, totalFee: Double): Double {
        return amount + totalFee
    }

    fun calculateTotalWhenIncludeFee(amount: Double, percentage: Double): Double {
        return amount - calculateFee(amount, percentage)
    }

    fun convertCurrencyOpposed(rate: Double, amount: Double): Double {
        return amount / rate
    }
}