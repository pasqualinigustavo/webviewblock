package com.webviewblock.navigator;

import androidx.lifecycle.MutableLiveData;

import com.webviewblock.util.SingleLiveEvent;

import java.io.Serializable;

public class SharedEvents {
    MutableLiveData<NavigationEventData> navigationEvent = new MutableLiveData<NavigationEventData>();
    SingleLiveEvent<Serializable> navigationResultEvent = new SingleLiveEvent<Serializable>();
}