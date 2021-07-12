package com.webviewblock.navigator;

import android.app.Activity;
import android.content.Intent;
import com.webviewblock.domain.enums.NavigationEvent;
import java.io.Serializable;

public class NavigationEventData {

    private NavigationEvent event;
    private Serializable data;
    private Activity activity;
    private Intent intent;

    public NavigationEventData(NavigationEvent event, Serializable data, Activity activity, Intent intent) {
        this.event = event;
        this.data = data;
        this.activity = activity;
        this.intent = intent;
    }

    public NavigationEvent getEvent() {
        return event;
    }

    public void setEvent(NavigationEvent event) {
        this.event = event;
    }

    public Serializable getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = data;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}