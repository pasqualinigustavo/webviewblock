package com.webviewblock.navigator;

import android.content.Context;
import android.os.Bundle;

import androidx.navigation.NavController;

import java.io.Serializable;

public class NavigationController {

    private int currentId = -1;
    public String DATA_KEY = "data";
    private NavController navController;
    private Context context;

    public NavigationController(NavController navController ,
                                Context context) {
        this.navController = navController;
                this.context = context;
    }

    int getCurrentId() {
        if(navController.getCurrentDestination() != null)
            return navController.getCurrentDestination().getId();
        return -1;
    }

    void navigate(int resId, Serializable args) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_KEY, args);
        navController.navigate(resId, bundle);
    }

    public boolean finish() {
        return navController.popBackStack();
    }
}
