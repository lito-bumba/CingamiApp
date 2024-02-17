package com.bumba.cingami.app.core.data

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class PreferenceManager(private val settings: Settings) {

    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    fun setString(key: String, value: String) {
        observableSettings.set(key = key, value = value)
    }

    fun getString(key: String) = observableSettings.getString(
        key = key,
        defaultValue = ""
    )

    fun setInt(key: String, value: Int) {
        observableSettings.set(key = key, value = value)
    }

    fun getInt(key: String) = observableSettings.getIntOrNull(key = key)

    fun clearPreferences() {
        observableSettings.clear()
    }

    fun getDouble(key: String): Double {
        return observableSettings
            .getDouble(key = key, defaultValue = 0.0)
    }

    fun setDouble(key: String, value: Double) {
        observableSettings.set(key = key, value = value)
    }
}