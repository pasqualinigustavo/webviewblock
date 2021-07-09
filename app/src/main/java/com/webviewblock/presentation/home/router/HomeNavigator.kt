package com.webviewblock.presentation.home.router

import com.webviewblock.R
import com.webviewblock.domain.enums.NavigationEvent
import com.webviewblock.navigator.NavigationController
import com.webviewblock.navigator.NavigationEventData
import com.webviewblock.navigator.Navigator
import com.webviewblock.navigator.SharedEvents
import timber.log.Timber

open class HomeNavigator(sharedEvents: SharedEvents) : Navigator(sharedEvents) {

    override fun handleNavigationEvent(
        navigationState: NavigationEventData,
        navController: NavigationController
    ): Boolean {
        Timber.d("HomeNavigator navigation destination: " + navigationState.event.name)
        when (navigationState.event) {
            NavigationEvent.SEARCH -> {
                navigate(
                    R.id.searchFragment,
                    navController,
                    navigationState.data
                )
            }
            NavigationEvent.SETTINGS -> {
                navigate(
                    R.id.settingsFragment,
                    navController,
                    navigationState.data
                )
            }
            NavigationEvent.WEBVIEW -> {
//                navigate(
//                        R.id.cardsFragment,
//                        navController,
//                        navigationState.data
//                )
            }
            else -> {
                Timber.d("MainNavigator unknow navigation destination: " + navigationState.event.name)
                return false
            }
        }
        return true
    }
}