package com.bumba.cingami.app.presentation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.bumba.cingami.app.core.presentation.AppTheme
import com.bumba.cingami.app.presentation.convert.ConvertScreen

@Composable
fun App() {
    AppTheme {
        Navigator(ConvertScreen())
    }
}