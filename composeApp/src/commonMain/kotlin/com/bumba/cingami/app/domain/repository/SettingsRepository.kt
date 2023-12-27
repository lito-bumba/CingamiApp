package com.bumba.cingami.app.domain.repository

interface SettingsRepository {

    fun savePercentageFee(countryCode: String, fee: Double)

    fun saveCurrentRate(countryCode: String, rate: Double)

    fun getPercentageFee(countryCode: String): Double

    fun getCurrentRate(countryCode: String): Double

    fun clearAllData()
}