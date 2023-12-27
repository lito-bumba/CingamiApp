package com.bumba.cingami.app

import android.app.Application
import com.bumba.cingami.app.di.KoinInit
import com.bumba.cingami.app.di.androidModule
import org.jetbrains.compose.components.resources.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class CingamiApp: Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInit().init {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@CingamiApp)
            modules(
                listOf(androidModule)
            )
        }
    }
}