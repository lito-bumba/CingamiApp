package com.bumba.cingami.app.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.bumba.cingami.app.presentation.convert.ConvertScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(ConvertScreen())
    }
}