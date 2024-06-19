package com.bumba.cingami.app.core.util

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
actual fun Double.formatNumber(): String {
    return String.format("%.2f", this)
}