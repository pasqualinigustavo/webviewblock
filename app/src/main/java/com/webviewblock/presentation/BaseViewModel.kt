package com.webviewblock.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.webviewblock.navigator.Navigator
import java.io.Serializable

abstract class BaseViewModel
constructor(
    val navigator: Navigator,
) :
    ViewModel() {

    val params = MutableLiveData<Serializable>()

    open fun onAttached() {

    }
}

