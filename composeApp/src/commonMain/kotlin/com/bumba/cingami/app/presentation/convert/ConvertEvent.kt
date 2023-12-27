package com.bumba.cingami.app.presentation.convert

sealed interface ConvertEvent {
    data class ChooseCurrency(val code: String) : ConvertEvent
    data class ChangeAmountScreen(val amount: Double) : ConvertEvent
    data object Convert : ConvertEvent
    data object ConvertOpposed : ConvertEvent
    data object FeeIncluded : ConvertEvent
    data object ShareData : ConvertEvent
}