package com.bumba.cingami.app.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun AmountTextView(
    text: String,
    currencyCode: String,
    amount: String,
    isBold: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = "$text:",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            color = Color.Black
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Medium,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                ) {
                    append(currencyCode)
                }
                append(" ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Medium,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                ) {
                    append(amount)
                }
            },
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}