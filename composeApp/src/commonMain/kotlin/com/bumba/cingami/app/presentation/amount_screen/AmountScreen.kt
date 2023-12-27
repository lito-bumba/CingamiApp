package com.bumba.cingami.app.presentation.amount_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bumba.cingami.app.presentation.component.AnimatedButton
import com.bumba.cingami.app.presentation.component.CurrencyText
import com.bumba.cingami.app.presentation.convert.ConvertScreen
import com.bumba.cingami.app.presentation.util.toNumberFormatted
import com.bumba.cingami.app.presentation.util.toRealNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class AmountScreen(
    val currency: String,
    val amount: Double
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var fromAmount by rememberSaveable {
            mutableStateOf(if (amount <= 0) "0" else "$amount".toNumberFormatted())
        }

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
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                CurrencyText(
                    code = currency,
                    amount = fromAmount,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(48.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(buttonItems) { number ->
                        ButtonNumber(
                            number = number,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            val isNotDropLast = number != "<"

                            val isNotComma = number != ","
                            val notContainComma = !fromAmount.contains(",")
                            val shouldToInsertComma =
                                !isNotComma && notContainComma && fromAmount.isNotBlank()

                            if (isNotDropLast && isNotComma) {
                                fromAmount = if (fromAmount == "0") number else fromAmount + number
                            }

                            if (shouldToInsertComma) {
                                fromAmount += number
                            }

                            if (!isNotDropLast) {
                                fromAmount = fromAmount.dropLast(1)
                            }
                        }
                    }
                }
                var isLoading by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()
                Spacer(Modifier.height(32.dp))
                AnimatedButton(
                    text = "Converter",
                    isLoading = isLoading,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    progressIndicatorColor = Color.White,
                    modifier = Modifier.fillMaxWidth().padding(12.dp)
                ) {
                    coroutineScope.launch {
                        isLoading = true
                        delay(500)
                        navigator.push(
                            ConvertScreen(
                                currency = currency,
                                amount = fromAmount.toRealNumber(),
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ButtonNumber(
        modifier: Modifier = Modifier,
        number: String,
        onClick: () -> Unit
    ) {
        ElevatedButton(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = modifier
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    private val buttonItems = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ",", "0", "<")
}