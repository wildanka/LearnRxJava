package com.example.dan.learnrxjava

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.dan.learnrxjava.helper.SimpleRxSingleton
import com.example.dan.learnrxjava.model.PostingModel
import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {
    private val BASE_URL : String = "https://jsonplaceholder.typicode.com/";
    private var bag = CompositeDisposable()

    //region Simple Network Layer
    interface JSONPlaceholder {
        @GET("posts/{id}")
        fun getPost(
                @Path("id") String id
        ): Call<PostingModel>
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
}
