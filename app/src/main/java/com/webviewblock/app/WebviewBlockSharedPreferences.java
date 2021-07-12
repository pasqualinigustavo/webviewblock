package com.webviewblock.app;

import android.content.SharedPreferences;
import javax.inject.Singleton;

@Singleton
public class WebviewBlockSharedPreferences implements SharedPreferenceProvider {

    SharedPreferences sharedPreferences;
    public WebviewBlockSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key, Boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public int getInt(String key, Integer defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public void putInt(String key, Integer value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public void putLong(String key, Long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public long getLong(String key, Long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    @Override
    public void clearAll() {
        sharedPreferences.edit().clear().apply();
        sharedPreferences.edit().commit();
    }
}