package com.bumba.cingami.app.presentation.util

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterStyle

actual fun Double.formatNumber(): String {
    if (this <= 0) return "0,00"

    val numberFormatter = NSNumberFormatter()

    numberFormatter.setNumberStyle(NSNumberFormatterStyle.MAX_VALUE)
    return numberFormatter.stringFromNumber(NSNumber(this)) ?: ""
}