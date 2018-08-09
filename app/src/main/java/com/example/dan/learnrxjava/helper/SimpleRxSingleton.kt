package com.example.dan.learnrxjava.helper

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.disposables.CompositeDisposable

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
        someInfo.accept("7")

        //NOTE: Relays will never receive onError and onComplete Events

    }
}