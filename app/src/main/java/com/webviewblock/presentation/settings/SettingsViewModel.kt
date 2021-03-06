package com.webviewblock.presentation.settings

import androidx.lifecycle.MutableLiveData
import com.webviewblock.domain.History
import com.webviewblock.domain.interactors.GetSettingsUseCase
import com.webviewblock.navigator.Navigator
import com.webviewblock.presentation.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

class SettingsViewModel
@Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    @Named(Navigator.DASHBOARD) navigator: Navigator
) : BaseViewModel(
    navigator
) {

    private val historyList = ArrayList<History>()
    val onLoaded = MutableLiveData<Boolean>()

    fun historyList() = historyList

    override fun onAttached() {
        super.onAttached()
        getSettings()
    }

    private fun getSettings() {
        historyList.clear()
        historyList.addAll(getSettingsUseCase.execute())
        onLoaded.postValue(getSettingsUseCase.blockImagesPreference())
    }

    fun blockImages(block: Boolean) {
        getSettingsUseCase.setBlockImagesPreference(block)
    }

    fun clear() {
        getSettingsUseCase.clear()
        getSettings()
    }
}