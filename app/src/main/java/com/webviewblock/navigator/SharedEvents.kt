package com.webviewblock.navigator

import androidx.lifecycle.MutableLiveData
import com.webviewblock.util.SingleLiveEvent
import java.io.Serializable

open class SharedEvents {
    val navigationEvent = MutableLiveData<NavigationEventData>()
    val navigationResultEvent = SingleLiveEvent<Serializable>()
}