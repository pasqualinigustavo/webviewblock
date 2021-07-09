package com.webviewblock.app.repository

import com.google.gson.Gson
import com.webviewblock.app.SharedPreferenceProvider
import com.webviewblock.domain.History
import com.webviewblock.util.BehaviourSubjectDecorator
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSettingsRepository @Inject constructor(val preferences: SharedPreferenceProvider) {

    var history = initHistory()

    fun reset() {
        history = initHistory()
    }

    var blockImagesPreference: Boolean
        get() = preferences.getBoolean(BLOCK_IMAGES_TAG, false)
        set(value) = preferences.putBoolean(BLOCK_IMAGES_TAG, value)

    fun clear() = preferences.clearAll()

    private fun initHistory(): BehaviourSubjectDecorator<List<History>> {
        return object : BehaviourSubjectDecorator<List<History>>(BehaviorSubject.create()) {
            override fun onNext(t: List<History>) {
                super.onNext(t)
                history.onNext(readHistory())
            }
        }
    }

    private fun readHistory(): List<History> {
        var historyStr = preferences.getString(HISTORY_TAG, EMPTY)
        if(!historyStr.isNullOrEmpty()) {
            val gson = Gson()
            return gson.fromJson<List<History>>(historyStr, History::class.java)
        } else {
            return emptyList<History>()
        }
    }

    companion object {
        const val HISTORY_TAG = "HISTORY_TAG"
        const val BLOCK_IMAGES_TAG = "BLOCK_IMAGES_TAG"
        const val EMPTY = ""
    }
}
