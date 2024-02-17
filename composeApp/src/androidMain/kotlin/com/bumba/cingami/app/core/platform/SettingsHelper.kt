package com.bumba.cingami.app.core.platform

import android.content.Context
import android.content.SharedPreferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsHelper(
    private val context: Context
) {
    actual fun createSettings(): Settings {
        val delegate: SharedPreferences =
            context.getSharedPreferences("currency_and_fee", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}