package com.bumba.cingami.app.presentation.convert

data class ConvertState(
    val currentPercentageFee: Double = 0.0,
    val currentRate: Double = 0.0,
    val amountScreen: String = "",
    val fromCurrency: String = "",
    val toCurrency: String = "",
    val fromAmount: Double = 0.0,
    val toAmount: String = "",
    val totalFee: String = "",
    val isFeeIncluded: Boolean = false,
    val total: String = "0"
)
