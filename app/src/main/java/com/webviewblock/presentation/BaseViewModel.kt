package com.webviewblock.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.webviewblock.app.api.SchedulerProvider
import com.webviewblock.navigator.Navigator
import com.webviewblock.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

abstract class BaseViewModel
constructor(
    val schedulerProviderFacade: SchedulerProvider,
    val navigator: Navigator,
) :
    ViewModel() {

    val params = MutableLiveData<Serializable>()
    val loading = MutableLiveData<Boolean>()
    val disposables = CompositeDisposable()
    val showError = SingleLiveEvent<String>()

    override fun onCleared() {
        disposables.clear()
    }

    open fun onAttached() {

    }
}

