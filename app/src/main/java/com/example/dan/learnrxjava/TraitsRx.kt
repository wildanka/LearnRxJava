package com.jonbott.learningrxjava.SimpleExamples

import com.jonbott.learningrxjava.Common.disposedBy
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

object TraitsRx {
    var bag = CompositeDisposable()

    fun traits_single() {
        /*Sebenarnya cara kita membuat sebuah traits observable adalah sama saja (dapat dilihat pada line 14)
        * yang menjadi perbedaan adalah di line 28, yaitu onSuccess event single.onSuccess("nice work!") */

        /*Sedangkan pada line 83-84 file SimpleRxSingleton.kt, terlihat bahwa observer bisa memiliki 1 atau lebih nilai.
        * Namun dengan implikasi 1 Observer mungkin saja memiliki lebih dari 1 value*/

        /*Observable*/
        val single = Single.create<String> { single ->
            //do some logic here
            val success = true


            if (success) { //return a value
                single.onSuccess("nice work!")
            } else {
                val someException = IllegalArgumentException("some fake error")
                single.onError(someException)
            }
        }

        /*Subscriber*/
        single.subscribe({ result ->
            //do something with result, if success
            println("👻 single: ${ result }")
        }, { error ->
            //do something for error
        }).disposedBy(bag)
    }


    fun traits_completable() {
        //Completable don't have values Complete atau Error
        val completable = Completable.create { completable ->
            //do logic here
            val success = true

            if (success) {
                //this one is onComplete
                completable.onComplete()
            } else {
                //this one is onError
                val someException = IllegalArgumentException("some fake error")
                completable.onError(someException)
            }
        }

        completable.subscribe({
            //handle on complete
            println("👻 Completable completed")
        }, { error ->
            //do something for error
        }).disposedBy(bag)

    }

    fun traits_maybe() {
        /*Maybe can have three different state,
        it can have a result that we're gonna turn back, Complete, or Error*/
        val maybe = Maybe.create<String> { maybe ->
            //do something
            val success = true
            val hasResult = true


            if (success) {
                if (hasResult) {
                    maybe.onSuccess("some result") //if there is a result
                } else {
                    maybe.onComplete() //if complete
                }
            } else {
                val someException = IllegalArgumentException("some fake error") //if error
                maybe.onError(someException)
            }
        }

        maybe.subscribe({ result ->
            //do something with result
            println("👻 Maybe - result: ${ result }")
        }, { error ->
            //do something with the error
        }, {
            //do something about completing
            println("👻 Maybe - completed")
        }).disposedBy(bag)
    }
}