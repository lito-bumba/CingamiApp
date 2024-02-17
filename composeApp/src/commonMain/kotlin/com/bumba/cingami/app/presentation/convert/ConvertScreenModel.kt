package com.bumba.cingami.app.presentation.convert

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.bumba.cingami.app.core.platform.ShareHelper
import com.bumba.cingami.app.core.util.currentByCode
import com.bumba.cingami.app.core.util.formatNumber
import com.bumba.cingami.app.core.util.toCurrencyRate
import com.bumba.cingami.app.core.util.toDestinyCurrency
import com.bumba.cingami.app.core.util.toFormattedData
import com.bumba.cingami.app.core.util.toRealNumber
import com.bumba.cingami.app.data.convert.Convert
import com.bumba.cingami.app.domain.repository.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ConvertScreenModel(
    private val shareHelper: ShareHelper?,
    private val settingsRepository: SettingsRepository
) : ScreenModel {

    private var _state = MutableStateFlow(ConvertState())
    val state: StateFlow<ConvertState> = _state

    fun onEvent(event: ConvertEvent) {
        when (event) {
            is ConvertEvent.ChangeAmountScreen -> {
                _state.update {
                    it.copy(amountScreen = event.amount.formatNumber())
                }
            }

            is ConvertEvent.ChooseCurrency -> {
                if (event.code == state.value.fromCurrency)
                    return

                getCurrentData(event.code)

                coroutineScope.launch {
                    _state.update { it.copy(fromCurrency = "") }
                    delay(100)
                    _state.update {
                        it.copy(
                            fromCurrency = event.code,
                            toCurrency = event.code.currentByCode()?.toDestinyCurrency()?.code
                                ?: ""
                        )
                    }
                    convert(state.value)
                }
            }

            is ConvertEvent.Convert -> convert(state.value)
            is ConvertEvent.ConvertOpposed -> {
                with(state.value) {
                    val theRealAmount = Convert.convertCurrencyOpposed(
                        amount = amountScreen.toRealNumber(),
                        rate = currentRate
                    )
                    val fee = Convert.calculateFee(
                        value = theRealAmount,
                        percentage = currentPercentageFee
                    )
//                    val amountWhenFeeIsIncludedOrNull = if (!state.isFeeIncluded) null else
//                        Convert.calculateTotalWhenIncludeFee(
//                            amount = state.amountScreen.toRealNumber(),
//                            percentage = state.currentPercentageFee
//                        )
////                    val theRealAmount =
//                    amountWhenFeeIsIncludedOrNull ?: state.amountScreen.toRealNumber()
//
//                    val totalToReceive = Convert.convertCurrency(state.currentRate, theRealAmount)
//                    _state.update {
//                        it.copy(
//                            fromAmount = theRealAmount,
//                            toAmount = totalToReceive.formatNumber(),
//                            totalFee = fee.formatNumber(),
//                            total = totalToSend.formatNumber()
//                        )
//                    }
                }
            }

            is ConvertEvent.FeeIncluded -> {
                _state.update { it.copy(isFeeIncluded = !state.value.isFeeIncluded) }

                if (state.value.fromCurrency.isNotBlank()) {
                    convert(state.value)
                }
            }

            ConvertEvent.ShareData -> {
                if (state.value.fromAmount <= 0) return
                shareResult(state.value)
            }
        }
    }

    private fun shareResult(state: ConvertState) {
        coroutineScope.launch {
            with(state) {
                shareHelper?.share(
                    "Câmbio: $fromCurrency 1 = $toCurrency ${currentRate.toCurrencyRate()}\n" +
                            "Valor: $fromCurrency ${fromAmount.formatNumber()} = $toCurrency $toAmount\n" +
                            "Taxa de ${currentPercentageFee}% = $fromCurrency $totalFee\n\n" +
                            "Total a enviar: $fromCurrency $total\n" +
                            "Total a receber: $toCurrency $toAmount\n\n" +
                            Clock.System.now().toEpochMilliseconds().toFormattedData() +
                            "\nCingami App"
                )
            }
        }
    }

    private fun convert(state: ConvertState) {
        val amountWhenFeeIsIncludedOrNull = if (!state.isFeeIncluded) null else
            Convert.calculateTotalWhenIncludeFee(
                amount = state.amountScreen.toRealNumber(),
                percentage = state.currentPercentageFee
            )
        val theRealAmount = amountWhenFeeIsIncludedOrNull ?: state.amountScreen.toRealNumber()
        val fee = Convert.calculateFee(
            value = state.amountScreen.toRealNumber(),
            percentage = state.currentPercentageFee
        )
        val totalToSend = Convert.calculateTotal(amount = theRealAmount, totalFee = fee)
        val totalToReceive = Convert.convertCurrency(state.currentRate, theRealAmount)
        _state.update {
            it.copy(
                fromAmount = theRealAmount,
                toAmount = totalToReceive.formatNumber(),
                totalFee = fee.formatNumber(),
                total = totalToSend.formatNumber()
            )
        }
    }

    private fun getCurrentData(countryCode: String) {
        coroutineScope.launch {
            val currentPercentageFee = settingsRepository.getPercentageFee(countryCode)
            val currentRate = settingsRepository.getCurrentRate(countryCode)
            _state.update {
                it.copy(
                    currentPercentageFee = currentPercentageFee,
                    currentRate = currentRate
                )
            }
        }
    }
}