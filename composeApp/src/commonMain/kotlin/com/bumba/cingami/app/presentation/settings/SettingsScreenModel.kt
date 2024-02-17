package com.bumba.cingami.app.presentation.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.bumba.cingami.app.domain.repository.SettingsRepository
import com.bumba.cingami.app.core.util.Currency
import com.bumba.cingami.app.core.util.toRealNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val settingsRepository: SettingsRepository
) : ScreenModel {

    private var _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    init {
        onEvent(SettingsEvent.GetCurrentData(Currency.KWANZA.code))
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeCurrency -> {
                _state.update { it.copy(currencyCode = event.code) }
                getCurrentData(event.code)
            }

            is SettingsEvent.ChangeFeePercentage -> {
                _state.update { it.copy(percentageFee = event.fee) }
            }

            is SettingsEvent.ChangeRate -> {
                _state.update { it.copy(rate = event.rate) }
            }

            is SettingsEvent.SaveSettings -> {
                coroutineScope.launch {
                    with(state.value) {
                        val percentFeeNotBlank = percentageFee != ""
                        val rateNotBlank = rate != ""
                        val isNewPercentFee = currentPercentageFee != percentageFee.toRealNumber()
                        val rateNotLikeCurrent = currentRate != rate.toRealNumber()

                        if (!percentFeeNotBlank && !rateNotBlank) return@launch

                        _state.update { it.copy(isSavingSettings = true) }
                        if (percentFeeNotBlank && isNewPercentFee) {
                            settingsRepository.savePercentageFee(
                                countryCode = currencyCode,
                                fee = percentageFee.toRealNumber()
                            )
                        }
                        if (rateNotBlank && rateNotLikeCurrent) {
                            settingsRepository.saveCurrentRate(
                                countryCode = currencyCode,
                                rate = rate.toRealNumber()
                            )
                        }
                    }
                    delay(300)
                    getCurrentData(state.value.currencyCode)
                    event.snackBarHostState.showSnackbar("Dados Salvo")
                }
            }

            is SettingsEvent.GetCurrentData -> getCurrentData(event.countryCode)
        }
    }

    private fun getCurrentData(countryCode: String) {
        coroutineScope.launch {
            val percentageFee = settingsRepository.getPercentageFee(countryCode)
            val currentRate = settingsRepository.getCurrentRate(countryCode)
            _state.update {
                SettingsState(
                    currencyCode = countryCode,
                    currentPercentageFee = percentageFee,
                    currentRate = currentRate
                )
            }
        }
    }
}