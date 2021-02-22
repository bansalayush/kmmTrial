package com.scorpio.kmmnetworkapp.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.scorpio.kmmnetworkapp.shared.Greeting
import android.widget.TextView
import android.widget.Toast
import com.scorpio.kmmnetworkapp.shared.SpaceXSDK
import com.scorpio.kmmnetworkapp.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    val sdk = SpaceXSDK(DatabaseDriverFactory(this))
    val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
        mainScope.launch {
            kotlin.runCatching {
                sdk.getLaunches(false)
            }.onSuccess {
                Log.d("KMM LOGS", it.toString())
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
