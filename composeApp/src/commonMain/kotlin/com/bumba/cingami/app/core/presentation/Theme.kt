package com.bumba.cingami.app.core.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

const val primaryColor = 0xFF5643C9
private val lightColors = lightColorScheme(
    primary = Color(primaryColor),
    secondary = Color.White
)
private val darkColors = darkColorScheme(
    primary = Color.Black,
    secondary = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorSheme = when {
        darkTheme -> darkColors
        else -> lightColors
    }

    MaterialTheme(
        colorScheme = colorSheme,
        content = {
            Surface(
                content = content
            )
        }
    )
}