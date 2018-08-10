package com.example.dan.learnrxjava.helper

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jonbott.learningrxjava.Common.disposedBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


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
//        val someException = IllegalArgumentException("some trial error to learn")
//        behaviorSubject.onError(someException)
//        behaviorSubject.onNext(109) //this should be not going to show,
        // because by the time it gets this onNext event, the whole subject has closed down

        //2 onComplete
        /* onComplete event can be triggered only if there is onError event triggered,
        vice-versa if you onComplete event triggered that means onError event is not triggered*/
        behaviorSubject.onComplete()
        behaviorSubject.onNext(312314) //will never show
    }

    fun basicObservable (){
        //The Observable
        val observable = Observable.create<String>{ observer ->
            //The lambda is called for every subscriber - by default
            /* Be kinda careful
            make sure you pick up the right process to be subscribed,
            you don't wanna create 100 database entries if there is 100 different subscribers who want to know when that event is finished*/
            println("~~ Observable logic being triggered ~~")

            //Do work on a background thread *Recommended
            launch {
                delay(1000)
                observer.onNext("Some Value 23")
                observer.onComplete()
            }
        }

        //then we call the observable via subscriber
        observable.subscribe{ someString ->
            println("New Value : $someString")
        }.disposedBy(bag)

        val observer = observable.subscribe{ someString ->
            println("Another Value of Subscriber : $someString")
        }

        observer.disposedBy(bag)

    }
}