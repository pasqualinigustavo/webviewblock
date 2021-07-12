package com.webviewblock.navigator;

import android.app.Activity;
import android.content.Intent;
import com.webviewblock.domain.enums.NavigationEvent;
import java.io.Serializable;

public class NavigationEventData {

    private NavigationEvent event;
    private Serializable data;

    public NavigationEventData(NavigationEvent event, Serializable data) {
        this.event = event;
        this.data = data;
    }

    public NavigationEvent getEvent() {
        return event;
    }

    public Serializable getData() {
        return data;
    }

}