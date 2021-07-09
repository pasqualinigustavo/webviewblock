package com.webviewblock.presentation.search

import androidx.lifecycle.MutableLiveData
import com.webviewblock.domain.History
import com.webviewblock.domain.enums.NavigationEvent
import com.webviewblock.domain.interactors.GetSettingsUseCase
import com.webviewblock.navigator.Navigator
import com.webviewblock.presentation.BaseViewModel
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList

class SearchViewModel
@Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    @Named(Navigator.DASHBOARD) navigator: Navigator
) : BaseViewModel(
    navigator
) {

    private val historyList = ArrayList<History>()
    val onLoaded = MutableLiveData<Boolean>()

    override fun onAttached() {
        super.onAttached()
        getSettings()
    }

    private fun getSettings() {
        historyList.clear()
        historyList.addAll(getSettingsUseCase.execute())
        onLoaded.postValue(getSettingsUseCase.blockImagesPreference())
    }

    fun openSettingsPage() {
        navigator.dispatchNavigationEvent(NavigationEvent.SETTINGS)
    }

    fun addToHistory(url: String) {
        if (!historyList.contains(History("", url))) {
            historyList.add(0, History(Date().toString(), url))
            var maxSize = if (historyList.size > MAX_HISTORY) MAX_HISTORY else historyList.size
            getSettingsUseCase.updateHistory(historyList.subList(0, maxSize).toList())
        }
    }

    companion object {
        const val MAX_HISTORY = 5
    }

}