package com.bumba.cingami.app.core.di

import com.bumba.cingami.app.data.repository.SettingsRepositoryImpl
import com.bumba.cingami.app.core.data.PreferenceManager
import com.bumba.cingami.app.core.platform.Platform
import com.bumba.cingami.app.core.platform.getPlatform
import com.bumba.cingami.app.domain.repository.SettingsRepository
import com.bumba.cingami.app.presentation.convert.ConvertScreenModel
import com.bumba.cingami.app.presentation.settings.SettingsScreenModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule() = module {
    single<PreferenceManager> {
        PreferenceManager(settings = get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(preferenceManager = get())
    }

    single<ConvertScreenModel> {
        ConvertScreenModel(
            shareHelper = get(),
            settingsRepository = get()
        )
    }

    factory<SettingsScreenModel> {
        SettingsScreenModel(get())
    }

    single<Platform> { getPlatform() }
}

expect fun platformModule(): Module