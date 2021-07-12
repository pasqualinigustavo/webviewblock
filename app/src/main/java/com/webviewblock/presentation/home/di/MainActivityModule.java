package com.webviewblock.presentation.home.di;

import com.webviewblock.di.modules.FragmentBuildersModule;
import com.webviewblock.presentation.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract HomeActivity contributeHomeActivity();
}
