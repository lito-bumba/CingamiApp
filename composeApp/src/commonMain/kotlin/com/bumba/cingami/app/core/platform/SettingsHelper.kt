package com.bumba.cingami.app.core.platform

import com.russhwolf.settings.Settings

expect class SettingsHelper {

    fun createSettings(): Settings
}