package com.bumba.cingami.app.data.repository

import com.bumba.cingami.app.data.util.PreferenceManager
import com.bumba.cingami.app.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager
) : SettingsRepository {

    override fun savePercentageFee(countryCode: String, fee: Double) {
        preferenceManager.setDouble(PERCENTAGE_FEE + countryCode, fee)
    }

    override fun saveCurrentRate(countryCode: String, rate: Double) {
        preferenceManager.setDouble(CURRENT_RATE + countryCode, rate)
    }

    override fun getPercentageFee(countryCode: String): Double {
        return preferenceManager.getDouble(PERCENTAGE_FEE + countryCode)
    }

    override fun getCurrentRate(countryCode: String): Double {
        return preferenceManager.getDouble(CURRENT_RATE + countryCode)
    }

    override fun clearAllData() {
        preferenceManager.clearPreferences()
    }

    companion object {
        const val PERCENTAGE_FEE = "percent"
        const val CURRENT_RATE = "rate"
    }
}