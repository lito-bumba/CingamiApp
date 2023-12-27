package com.bumba.cingami.app.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CurrencyIcon(
    res: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(res),
        contentDescription = "country-flag",
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
    )
}