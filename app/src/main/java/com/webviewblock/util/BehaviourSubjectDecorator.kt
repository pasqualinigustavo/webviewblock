package com.webviewblock.util

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

open class BehaviourSubjectDecorator<T>(private val subject: BehaviorSubject<T>) : Observer<T> {

    var value: T? = null
        get() = subject.value

    fun observeOn(scheduler: Scheduler): Observable<T> {
        return subject.observeOn(scheduler)
    }

    fun subscribeOn(scheduler: Scheduler): Observable<T> {
        return subject.subscribeOn(scheduler)
    }

    fun subscribe(onNext: Consumer<in T>): Disposable {
        return subject.subscribe(onNext)
    }

    fun subscribe(onNext: Consumer<T>, onError: Consumer<in Throwable>): Disposable {
        return subject.subscribe(onNext, onError)
    }

    fun subscribe(
        onNext: Consumer<in T>,
        onError: Consumer<in Throwable>,
        onComplete: Action
    ): Disposable {
        return subject.subscribe(onNext, onError, onComplete)
    }

    override fun onNext(t: T) {
        subject.onNext(t)
    }

    fun hasThrowable(): Boolean {
        return subject.hasThrowable()
    }

    fun hasObservers(): Boolean {
        return subject.hasObservers()
    }

    override fun onComplete() {
        subject.onComplete()
    }

    override fun onSubscribe(d: Disposable) {
        subject.onSubscribe(d)
    }

    override fun onError(e: Throwable) {
        subject.onError(e)
    }

    fun getThrowable(): Throwable? {
        return subject.throwable
    }

    fun hasComplete(): Boolean {
        return subject.hasComplete()
    }
}
