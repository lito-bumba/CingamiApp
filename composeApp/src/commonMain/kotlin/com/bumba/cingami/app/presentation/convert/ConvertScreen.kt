package com.bumba.cingami.app.presentation.convert

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bumba.cingami.app.core.platform.Platform
import com.bumba.cingami.app.core.util.currentByCode
import com.bumba.cingami.app.core.util.formatNumber
import com.bumba.cingami.app.presentation.amount_screen.AmountScreen
import com.bumba.cingami.app.presentation.component.AmountTextView
import com.bumba.cingami.app.presentation.component.CurrencyText
import com.bumba.cingami.app.presentation.component.SelectCurrencySection
import com.bumba.cingami.app.presentation.settings.SettingsScreen
import org.koin.compose.rememberKoinInject

data class ConvertScreen(
    val currency: String = "",
    val amount: Double = 0.0
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberKoinInject<ConvertScreenModel>()
        val currentPlatform = rememberKoinInject<Platform>()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.onEvent(ConvertEvent.ChangeAmountScreen(amount))
            screenModel.onEvent(ConvertEvent.ChooseCurrency(currency))
            if (state.fromCurrency.isNotBlank()) {
                screenModel.onEvent(ConvertEvent.Convert)
            }
        }

        Scaffold(
            topBar = {
                TopBarApp(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 16.dp
                        )
                ) {
                    navigator.push(SettingsScreen)
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { screenModel.onEvent(ConvertEvent.ShareData) },
                    shape = RoundedCornerShape(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(70.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(all = 12.dp)
                            .fillMaxSize()
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ) {
                CurrencyText(
                    code = state.fromCurrency,
                    amount = state.amountScreen,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.push(
                                AmountScreen(
                                    currency = state.fromCurrency,
                                    amount = state.fromAmount
                                )
                            )
                        }
                        .padding(16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Checkbox(
                        checked = state.isFeeIncluded,
                        onCheckedChange = {
                            screenModel.onEvent(ConvertEvent.FeeIncluded)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.White,
                            checkmarkColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Incluir a taxa nesse valor",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier
                            .clickable { screenModel.onEvent(ConvertEvent.FeeIncluded) }
                    )
                }
                Surface(
                    tonalElevation = 13.dp,
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.elevatedCardElevation(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "Enviar\nde:",
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                        .padding(start = 16.dp)
                                )
                                SelectCurrencySection(
                                    currentCurrency = state.fromCurrency.currentByCode(),
                                    modifier = Modifier.padding(all = 8.dp)
                                ) { currency ->
                                    screenModel.onEvent(ConvertEvent.ChooseCurrency(currency.code))
                                }
                            }
                        }
                        Spacer(Modifier.height(32.dp))
                        AnimatedVisibility(
                            visible = state.fromCurrency.isNotBlank() && state.fromAmount > 0
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 16.dp)
                            ) {
                                Text(
                                    text = "${state.fromCurrency} 1 = ${state.toCurrency} " +
                                            state.currentRate,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(Modifier.height(8.dp))
                                Divider()
                                Spacer(Modifier.height(8.dp))
                                AmountTextView(
                                    text = "Valor",
                                    currencyCode = state.fromCurrency,
                                    amount = state.fromAmount.formatNumber(),
                                    modifier = Modifier
                                )
                                Spacer(Modifier.height(8.dp))
                                AmountTextView(
                                    text = "Taxa de ${state.currentPercentageFee.formatNumber()}%",
                                    currencyCode = state.fromCurrency,
                                    amount = state.totalFee,
                                    modifier = Modifier
                                )
                                Spacer(Modifier.height(8.dp))
                                Divider()
                                Spacer(Modifier.height(8.dp))
                                AmountTextView(
                                    text = "Total a enviar",
                                    currencyCode = state.fromCurrency,
                                    amount = state.total,
                                    isBold = true,
                                    modifier = Modifier
                                )
                                Spacer(Modifier.height(8.dp))
                                AmountTextView(
                                    text = "Total a Receber",
                                    currencyCode = state.toCurrency,
                                    amount = state.toAmount,
                                    isBold = true,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBarApp(
        modifier: Modifier,
        onClick: () -> Unit
    ) {
        val platform = rememberKoinInject<Platform>()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (platform != Platform.IOS) Arrangement.SpaceBetween else Arrangement.End,
            modifier = modifier
        ) {
            if (platform != Platform.IOS) {
                Text(
                    text = "Cingami App",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
            IconButton(
                onClick = onClick,
                modifier = Modifier.padding(top = (if (platform == Platform.IOS) 24 else 0).dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}