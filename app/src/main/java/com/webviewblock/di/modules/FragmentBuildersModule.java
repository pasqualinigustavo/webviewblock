package com.webviewblock.di.modules;

import com.webviewblock.presentation.search.SearchFragment;
import com.webviewblock.presentation.settings.SettingsFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();
}
