package com.test.indoorlocation

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

object RxPublisher {

    private val pointPublisher: PublishSubject<DoubleArray> = PublishSubject.create()

    fun subscribeToPoint(observer: Observer<DoubleArray>) {
        pointPublisher
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }

    fun publishPoint(point: DoubleArray) {
        pointPublisher.onNext(point)
    }
}