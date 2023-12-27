package com.bumba.cingami.app.presentation.util

import android.icu.text.NumberFormat
import java.util.Locale

actual fun Double.formatNumber(): String {
    if (this <= 0.0) return "0"
    val formatter = NumberFormat.getNumberInstance(Locale("pt", "BR"))
    formatter.minimumFractionDigits = 2
    return formatter.format(this)
}