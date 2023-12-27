package com.bumba.cingami.app.platform

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class SettingsHelper {

    actual fun createSettings(): Settings {
        val delegate = NSUserDefaults.standardUserDefaults
        return NSUserDefaultsSettings(delegate)
    }
}