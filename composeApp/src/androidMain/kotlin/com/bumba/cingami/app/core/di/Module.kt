package com.bumba.cingami.app.core.di

import com.bumba.cingami.app.core.platform.Platform
import com.bumba.cingami.app.core.platform.SettingsHelper
import com.bumba.cingami.app.core.platform.ShareHelper
import com.bumba.cingami.app.core.platform.getPlatform
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { ShareHelper(context = get()) }
    single { SettingsHelper(context = get()).createSettings() }
}