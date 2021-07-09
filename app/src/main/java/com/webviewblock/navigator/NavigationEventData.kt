package com.webviewblock.navigator

import android.app.Activity
import android.content.Intent
import com.webviewblock.domain.enums.NavigationEvent
import java.io.Serializable

data class NavigationEventData(val event: NavigationEvent, val data: Serializable? = null, val activity: Activity? = null, val intent: Intent? = null)