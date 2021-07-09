package com.webviewblock.di.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.webviewblock.app.*
import com.webviewblock.app.api.*
import com.webviewblock.application.WebviewBlockApplication
import com.webviewblock.di.ViewModelModule
import com.webviewblock.navigator.Navigator
import com.webviewblock.navigator.SharedEvents
import com.webviewblock.presentation.home.router.HomeNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideScheduleProvider(): SchedulerProvider {
        return SchedulersFacade()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceProvider(sharedPreferences: SharedPreferences): SharedPreferenceProvider {
        return WebviewBlockSharedPreferences(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideApp(app: Application): WebviewBlockApplication {
        return app as WebviewBlockApplication
    }

    @Singleton
    @Provides
    @Named(Navigator.DASHBOARD)
    fun provideDashboardNavigator(sharedEvents: SharedEvents): Navigator {
        return HomeNavigator(sharedEvents)
    }

    @Singleton
    @Provides
    fun bindSharedEvents(): SharedEvents {
        return SharedEvents()
    }

}