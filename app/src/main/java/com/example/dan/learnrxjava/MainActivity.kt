package com.example.dan.learnrxjava

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.dan.learnrxjava.entities.Posting
import com.example.dan.learnrxjava.helper.SimpleRxSingleton
import com.jonbott.learningrxjava.Common.disposedBy
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val BASE_URL : String = "https://jsonplaceholder.typicode.com/";
    private var bag = CompositeDisposable()

    //region Simple Network Layer
    interface JSONPlaceholder {
        @GET("posts/{id}")
        fun getPost(@Path("id") id : String): Call<Posting>
    }

    private var retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    private var service = retrofit.create(JSONPlaceholder::class.java)
    //endregion

    //region Life Cycle Events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realSingleExample()
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    //Region RxCode
    private fun realSingleExample(){
        //pada fungsi ini kita melakukan konsumsi terhadap Subscription/Observable yang telah kita buat
//        var posting: Posting;
        loadPostAsSingle().observeOn(AndroidSchedulers.mainThread())//whatever happened just make sure it's running on main thread
                .subscribeOn(Schedulers.io())
                .subscribe({ posting ->
                    tvUserID.text = posting.userId.toString()
                    tvPostContent.text = posting.body.toString()
                },{error ->//error
                    println("! an Error occured : ${ error.localizedMessage }")
                    tvUserID.text = ""
                    tvPostContent.text = ""
                }).disposedBy(bag)
    }

    private fun loadPostAsSingle(): Single<Posting>{
        //pada method ini kita membuat sebuah Subscription/Observable
        return Single.create{ observer ->
            //Simulate Network Delay
            Thread.sleep(2000)
            val postingId = 5
            service.getPost(postingId.toString())
                    .enqueue(object: Callback<Posting?> {
                        override fun onResponse(call: Call<Posting?>?, response: Response<Posting?>?) {
                            val posting = response?.body()

                            if (posting != null ){
                                observer.onSuccess(posting)
                            }else {
                                val e = IOException("An unknown network error occured")
                                observer.onError(e)
                            }
                        }

                        override fun onFailure(call: Call<Posting?>?, t: Throwable?) {
                            val e = t ?: IOException("An unknown network error occured")
                            observer.onError(e)
                        }

                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }

    //endregion
}
