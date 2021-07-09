package com.webviewblock.di.components

import android.app.Application
import com.webviewblock.application.WebviewBlockApplication
import com.webviewblock.di.modules.AppModule
import com.webviewblock.presentation.home.di.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        MainActivityModule::class,
        AndroidInjectionModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WebviewBlockApplication)
}