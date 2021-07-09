package com.webviewblock.presentation.home

import com.webviewblock.app.api.SchedulerProvider
import com.webviewblock.navigator.Navigator
import com.webviewblock.presentation.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

class HomeActivityViewModel
@Inject constructor(
    schedulerProviderFacade: SchedulerProvider,
    @Named(Navigator.DASHBOARD) navigator: Navigator
) : BaseViewModel(
    schedulerProviderFacade,
    navigator
) {

}