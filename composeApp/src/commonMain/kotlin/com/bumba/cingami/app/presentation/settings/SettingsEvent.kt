package com.bumba.cingami.app.presentation.settings

import androidx.compose.material3.SnackbarHostState

sealed interface SettingsEvent {
    data class GetCurrentData(val countryCode: String) : SettingsEvent
    data class ChangeCurrency(val code: String) : SettingsEvent
    data class ChangeFeePercentage(val fee: String) : SettingsEvent
    data class ChangeRate(val rate: String) : SettingsEvent
    data class SaveSettings(val snackBarHostState: SnackbarHostState) : SettingsEvent
}