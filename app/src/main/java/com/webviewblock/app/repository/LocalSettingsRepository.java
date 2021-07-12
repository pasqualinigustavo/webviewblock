package com.webviewblock.app.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webviewblock.app.SharedPreferenceProvider;
import com.webviewblock.domain.History;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.internal.Factory;

public final class LocalSettingsRepository {

    private SharedPreferenceProvider preferences;
    boolean blockImagesPreference = false;
    private String HISTORY_TAG = "HISTORY_TAG";
    private String BLOCK_IMAGES_TAG = "BLOCK_IMAGES_TAG";
    private String EMPTY = "";

    @Inject
    public LocalSettingsRepository(SharedPreferenceProvider preferences) {
        this.preferences = preferences;
    }

    public void clear() {
        preferences.clearAll();
    }

    public List<History> getHistory() {
        String historyStr = preferences.getString(HISTORY_TAG, EMPTY);
        try {
            if (historyStr != null) {
                Type typeOfObjectsListNew = new TypeToken<ArrayList<History>>() {
                }.getType();
                return new Gson().fromJson(historyStr, typeOfObjectsListNew);
            } else {
                return new ArrayList<History>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<History>();
    }

    public void updateHistory(List<History> historyList) {
        preferences.putString(HISTORY_TAG, new Gson().toJson(historyList));
    }

    public boolean isBlockImagesPreference() {
        return blockImagesPreference;
    }

    public void setBlockImagesPreference(boolean blockImagesPreference) {
        this.blockImagesPreference = blockImagesPreference;
    }
}
