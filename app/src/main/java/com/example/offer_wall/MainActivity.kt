package com.example.offer_wall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var resetTextView: TextView
    private lateinit var sharedPreference: SharedPreference

    companion object {
        const val TAG = "main activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetTextView = findViewById(R.id.reset)
        sharedPreference = SharedPreference(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun allowedService(): AllowedService {
            return retrofit.create(AllowedService::class.java)
        }

        val call = allowedService().getAllowedBoolean()

        if (!sharedPreference.containsPreference("ALLOW_PREFERENCES")) {
            requestAllowedBoolean(call)
        } else {
            beginTransaction()
        }

        resetTextView.setOnClickListener {
            val newCall = call.clone()
            requestAllowedBoolean(newCall)
        }
    }

    private fun requestAllowedBoolean(call: Call<AllowedBooleanObject>){
        call.enqueue(object : Callback<AllowedBooleanObject> {
            override fun onResponse(
                call: Call<AllowedBooleanObject>,
                response: Response<AllowedBooleanObject>
            ) {
                if (!response.isSuccessful) {
                    Log.v(TAG, "Response ${response.code()}")
                } else {
                    saveSetting(response.body()!!.allowedBoolean)
                    beginTransaction()
                }
            }
            override fun onFailure(
                call: Call<AllowedBooleanObject>?,
                t: Throwable?
            ) {
                Log.v(TAG, "failure" + t?.message)
            }
        })
    }

    private fun saveSetting(allowedBoolean: String) {
        if (allowedBoolean == "true") {
            sharedPreference.setSharedPreference("ALLOW_PREFERENCES", true)
        } else {
            sharedPreference.setSharedPreference("ALLOW_PREFERENCES", false)
        }
    }

    private fun beginTransaction() {
        if (sharedPreference.getSharedPreference("ALLOW_PREFERENCES")) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment, WebViewFragment()).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.fragment, GameFragment()).commit()
        }
    }
}
