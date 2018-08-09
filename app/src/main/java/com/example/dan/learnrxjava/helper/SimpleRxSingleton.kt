package com.example.dan.learnrxjava.helper

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by DAN on 8/10/2018.
 */
object SimpleRxSingleton{
    var bag = CompositeDisposable()
    fun simpleValues(){
        println("~~~~~~simpleValues~~~~~~")

        val someInfo = BehaviorRelay.createDefault("1")
        println("TES someInfo.value ${someInfo.value}")

        val plainString = someInfo.value
        println("TES plainString : $plainString")

        someInfo.accept("2")
        println("TES someInfo.value ${someInfo.value} , $plainString")

        someInfo.subscribe{newValue ->
            println(" value has changed : $newValue")
        }

        someInfo.accept("3")
        someInfo.accept("7as")

        //NOTE: Relays will never receive onError and onComplete Events
    }

    fun subjects(){
        val behaviorSubject = BehaviorSubject.createDefault(24)

        val disposable = behaviorSubject.subscribe({newValue -> //onNext
            println("behaviourSubject Subscription : $newValue")
        },{error -> //onError
            println("onError : ${error.localizedMessage}")
        },{ //onCompleted
            println("onCompleted : ")
        },{ disposable -> //onSubscribed
            println("Subscribed")
        })

        behaviorSubject.onNext(34)
        behaviorSubject.onNext(48)
        behaviorSubject.onNext(48) //duplicates show as new events by default

        //1 onError
        val someException = IllegalArgumentException("some trial error to learn")
        behaviorSubject.onError(someException)
        behaviorSubject.onNext(109) //this should be not going to show,
        // because by the time it gets this onNext event, the whole subject has closed down

    }
}