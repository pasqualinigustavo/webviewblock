package com.webviewblock.util.extensions

import com.webviewblock.app.api.SchedulerProvider
import com.webviewblock.presentation.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber


/**
 * convenience method that applies scheduling, sets loading models and applies error handling
 */
fun <T> Observable<T>.subscribe(
    viewModel: BaseViewModel,
    onNext: (t: T) -> Unit,
    onError: (t: Throwable) -> Unit = {},
    retryHandler: (() -> Unit)? = null
): Disposable {
    return this.applySchedulers(viewModel.schedulerProviderFacade)
        .doOnSubscribe { viewModel.loading.value = true }
        .applyErrorHandling(viewModel, retryHandler)
        .doOnNext { viewModel.loading.value = false }
        .doOnError { e ->
            viewModel.loading.value = false
            Timber.e(e)
        }
        .subscribe(onNext, onError)
}


/**
 * apply app wide error handling, forbidden and unauthorised must be handled in a specific way according to business rules.
 * If a retryHandler is provided then default error handling will be provided for network and general bad request errors.
 * The error will always be propagated to the subscriber for optional additional handling
 */
fun <T> Observable<T>.applyErrorHandling(
    viewModel: BaseViewModel,
    recoveryHandler: (() -> Unit)? = null
): Observable<T> {
    return this.doOnSubscribe {
    }.onErrorResumeNext { throwable: Throwable ->
        Observable.error(throwable)
    }
}

fun <T> Observable<T>.applySchedulers(schedulerProviderFacade: SchedulerProvider): Observable<T> {
    return this.subscribeOn(schedulerProviderFacade.io())
        .observeOn(schedulerProviderFacade.ui())

}

/**
 * convenience method that applies scheduling, sets loading models and applies error handling
 */
fun <T> Observable<T>.subscribeWithDefaultHandlers(
    viewModel: BaseViewModel,
    retryHandler: () -> Unit,
    onNext: (t: T) -> Unit,
    onError: (t: Throwable) -> Unit = {}
): Disposable {
    return subscribe(viewModel, onNext, onError, retryHandler)
}

fun <T> of(): (T) -> Unit = {}