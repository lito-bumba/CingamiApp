package com.bumba.cingami.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bumba.cingami.app.core.util.currentByCode
import com.bumba.cingami.app.presentation.component.ProgressButton
import com.bumba.cingami.app.presentation.component.SelectCurrencySection
import com.bumba.cingami.app.presentation.component.TextFieldWithLabelAbove
import com.bumba.cingami.app.core.util.formatNumber
import com.bumba.cingami.app.core.util.toCurrencyRate
import org.koin.compose.rememberKoinInject

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberKoinInject<SettingsScreenModel>()
        val state by screenModel.state.collectAsState()
        val focusManager = LocalFocusManager.current
        val snackBarHostState = remember { SnackbarHostState() }



        Scaffold(
            topBar = {
                IconButton(
                    onClick = { navigator.popUntilRoot() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Button Back"
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
            ) {
                SelectCurrencySection(
                    currentCurrency = state.currencyCode.currentByCode(),
                    modifier = Modifier
                ) { currency ->
                    screenModel.onEvent(SettingsEvent.ChangeCurrency(currency.code))
                }
                Spacer(Modifier.height(16.dp))
                Divider()
                Spacer(Modifier.height(24.dp))
                TextFieldWithLabelAbove(
                    labelAboveTextField = "O câmbio corrente é de " +
                            state.currentRate.toCurrencyRate(),
                    labelInTextField = "Câmbio",
                    value = state.rate,
                    onValueChange = { rate ->
                        screenModel.onEvent(SettingsEvent.ChangeRate(rate))
                    },
                    isSingleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                TextFieldWithLabelAbove(
                    labelAboveTextField = "A percentagem da taxa actual é de " +
                            "${state.currentPercentageFee.formatNumber()}%",
                    labelInTextField = "Percentagem",
                    value = state.percentageFee,
                    onValueChange = { fee ->
                        screenModel.onEvent(SettingsEvent.ChangeFeePercentage(fee))
                    },
                    isSingleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        screenModel.onEvent(SettingsEvent.SaveSettings(snackBarHostState))
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(32.dp))
                ProgressButton(
                    text = "Salvar",
                    isLoading = state.isSavingSettings,
                    onClick = {
                        screenModel.onEvent(SettingsEvent.SaveSettings(snackBarHostState))
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.width(160.dp)
                )
            }
        }
    }
}