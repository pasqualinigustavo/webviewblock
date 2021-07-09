package com.webviewblock.di.modules

import com.webviewblock.presentation.search.SearchFragment
import com.webviewblock.presentation.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

//    @ContributesAndroidInjector
//    abstract fun contributeWebviewFragment(): WebviewFragment
}
