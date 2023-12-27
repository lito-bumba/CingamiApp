package com.bumba.cingami.app.di

import com.bumba.cingami.app.platform.SettingsHelper
import com.bumba.cingami.app.platform.ShareHelper
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { ShareHelper(context = get()) }
    single { SettingsHelper(context = get()).createSettings() }
}