package com.webviewblock.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.webviewblock.di.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class WebviewBlockApplication extends Application implements HasActivityInjector {

    private WebviewBlockApplication instance = null;

    @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    String TAG = "WebviewBlockApplication";
    Context appContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    void init() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        instance = this;
        // Context
        appContext = this.getApplicationContext();
        instance = this;

        AppInjector.init(this);
        initTimber();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_COMPLETE:
                this.tryToCleanMemory();
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                this.tryToCleanMemory();
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                this.cleanMemoryCache();
                break;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
        this.cleanMemoryCache();
    }

    private void tryToCleanMemory() {
        this.cleanMemoryCache();
        System.gc();
    }

    private void cleanMemoryCache() {

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
