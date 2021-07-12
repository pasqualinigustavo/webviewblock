package com.webviewblock.app;

public interface SharedPreferenceProvider {
    String getString(String key, String defValue);

    void putString(String key, String value);

    boolean getBoolean(String key, Boolean defValue);

    void putBoolean(String key, Boolean value);

    int getInt(String key, Integer defValue);

    void putInt(String key, Integer value);

    void putLong(String key, Long value);

    long getLong(String key, Long defValue);

    void clearAll();
}