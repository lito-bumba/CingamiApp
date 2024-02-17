package com.bumba.cingami.app.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumba.cingami.app.core.util.Currency

@Composable
fun SelectCurrencySection(
    currentCurrency: Currency?,
    modifier: Modifier = Modifier,
    onClick: (Currency) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(Currency.entries.toTypedArray()) { currency ->
            val colorIfIsSelected: Color by animateColorAsState(
                targetValue = if (currentCurrency == currency) MaterialTheme.colorScheme.primary else Color.Transparent,
                animationSpec = tween(durationMillis = 500, delayMillis = 100),
                label = ""
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(100.dp, 80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Transparent)
                    .border(4.dp, colorIfIsSelected, RoundedCornerShape(20.dp))
                    .clickable(onClick = { onClick(currency) })
            ) {
                CurrencyIcon(
                    res = currency.resImage,
                    modifier = Modifier.size(80.dp, 60.dp)
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}