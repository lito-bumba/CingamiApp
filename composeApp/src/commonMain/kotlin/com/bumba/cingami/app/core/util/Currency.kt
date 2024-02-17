package com.bumba.cingami.app.core.util

enum class Currency(val code: String, val countryName: String, val resImage: String) {
    KWANZA("Kz", "Angola", "flag-ao.xml"),
    REAL("R$", "Brasil", "flag-br.xml")
}

fun String.currentByCode(): Currency? {
    return Currency.entries.find { it.code == this }
}

fun Currency.toDestinyCurrency(): Currency {
    return if (this == Currency.KWANZA) {
        Currency.REAL
    } else {
        Currency.KWANZA
    }
}