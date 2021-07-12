package com.webviewblock.navigator

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.webviewblock.domain.enums.NavigationEvent
import com.webviewblock.util.SingleLiveEvent
import java.io.Serializable

abstract class Navigator(val sharedEvents: SharedEvents) {

    val navigationEvent: MutableLiveData<NavigationEventData>
        get() {
            return sharedEvents.navigationEvent
        }

    fun observeEvents(owner: LifecycleOwner, navController: NavigationController) {
        navigationEvent.observe(owner, Observer {
            if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                if (it != null && handleNavigationEvent(it, navController)) {
                    navigationEvent.value = null
                }
        })
    }

    abstract fun handleNavigationEvent(
        navigationState: NavigationEventData,
        navController: NavigationController
    ): Boolean

    fun dispatchNavigationEvent(event: NavigationEvent) {
        navigationEvent.value =
            NavigationEventData(event, null)
    }

    fun navigate(locationId: Int, navController: NavigationController, data: Serializable? = null) {
        sharedEvents.navigationEvent.value = NavigationEventData(NavigationEvent.NOP, null)
        locationId.takeIf { it != navController.currentId }
            ?.let {
                navController.navigate(it, data)
            }
    }

    companion object {
        const val DASHBOARD = "dash"
    }
}
