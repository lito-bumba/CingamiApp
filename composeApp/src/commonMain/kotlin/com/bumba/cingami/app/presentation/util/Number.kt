package com.bumba.cingami.app.presentation.util

fun String.toRealNumber(): Double {
    val containsComma = this.contains(',')
    val containsPoint = this.contains('.')
    val removePoints = this.replace(".", "")
    val formatToDouble = removePoints.replace(",", ".")

    return when {
        this.isBlank() -> 0.0
        containsPoint && containsComma -> formatToDouble.toDouble()
        containsComma -> formatToDouble.toDouble()
        else -> this.toDouble()
    }
}

fun String.toNumberFormatted(): String {
    return this.toRealNumber().formatNumber()
}

fun Double.toCurrencyRate(): String {
    return this.toString()
        .replace(".", ",")
}