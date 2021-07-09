package com.webviewblock.presentation.search

import com.webviewblock.app.api.SchedulerProvider
import com.webviewblock.domain.enums.NavigationEvent
import com.webviewblock.navigator.Navigator
import com.webviewblock.presentation.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

class SearchViewModel
@Inject constructor(
    schedulerProviderFacade: SchedulerProvider,
    @Named(Navigator.DASHBOARD) navigator: Navigator
) : BaseViewModel(
    schedulerProviderFacade,
    navigator
) {

    fun openSettingsPage() {
        navigator.dispatchNavigationEvent(NavigationEvent.SETTINGS)
    }

    fun addToHistory(url: String) {

    }
}