package com.webviewblock.presentation.home.router;

import com.webviewblock.R;
import com.webviewblock.navigator.NavigationController;
import com.webviewblock.navigator.NavigationEventData;
import com.webviewblock.navigator.Navigator;
import com.webviewblock.navigator.SharedEvents;

import javax.inject.Inject;

import timber.log.Timber;

public class HomeNavigator extends Navigator {

    @Inject
    public HomeNavigator(SharedEvents events) {
        super(events);
    }

    @Override
    public boolean handleNavigationEvent(NavigationEventData navigationState, NavigationController navController) {
        Timber.d("HomeNavigator navigation destination: " + navigationState.getEvent().name());
        switch (navigationState.getEvent()) {
            case SEARCH:
                navigate(
                        R.id.searchFragment,
                        navController,
                        navigationState.getData()
                );
                break;
            case SETTINGS:
                navigate(
                        R.id.settingsFragment,
                        navController,
                        navigationState.getData()
                );
                break;
            default:
                Timber.d("MainNavigator unknow navigation destination: " + navigationState.getEvent().name());
                break;
        }
        return false;
    }
}