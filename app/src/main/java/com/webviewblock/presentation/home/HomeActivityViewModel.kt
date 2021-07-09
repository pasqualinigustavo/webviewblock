package com.webviewblock.presentation.home

import com.webviewblock.navigator.Navigator
import com.webviewblock.presentation.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

class HomeActivityViewModel
@Inject constructor(
    @Named(Navigator.DASHBOARD) navigator: Navigator
) : BaseViewModel(
    navigator
) {

}