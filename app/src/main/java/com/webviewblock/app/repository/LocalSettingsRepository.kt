package com.webviewblock.app.repository

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.webviewblock.app.SharedPreferenceProvider
import com.webviewblock.domain.History
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSettingsRepository @Inject constructor(private val preferences: SharedPreferenceProvider) {

    var blockImagesPreference: Boolean
        get() = preferences.getBoolean(BLOCK_IMAGES_TAG, false)
        set(value) = preferences.putBoolean(BLOCK_IMAGES_TAG, value)

    fun clear() = preferences.clearAll()

    fun getHistory(): List<History> {
        var historyStr = preferences.getString(HISTORY_TAG, EMPTY)
        return try {
            if (!historyStr.isNullOrEmpty()) {
                val listType: Type = object : TypeToken<List<History?>?>() {}.type
                Gson().fromJson(historyStr, listType)
            } else {
                emptyList<History>()
            }
        } catch (e: Exception) {
            emptyList<History>()
        }
    }

    fun updateHistory(historyList: ArrayList<History>) {
        preferences.putString(HISTORY_TAG, Gson().toJson(historyList))
    }

    companion object {
        const val HISTORY_TAG = "HISTORY_TAG"
        const val BLOCK_IMAGES_TAG = "BLOCK_IMAGES_TAG"
        const val EMPTY = ""
    }
}
