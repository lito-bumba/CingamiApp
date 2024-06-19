package com.bumba.cingami.app.presentation.settings

import com.bumba.cingami.app.core.util.Currency

data class SettingsState(
    val currencyCode: String = Currency.KWANZA.code,
    val currentPercentageFee: Double = 0.0,
    val currentRate: Double = 0.0,
    val rate: String = "",
    val percentageFee: String = "",
    val isSavingSettings: Boolean = false
)