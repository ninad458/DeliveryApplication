package com.enigma.myapplication

import android.app.Application
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class App : Application() {

    private val scope = MainScope()

    override fun onCreate() {
        super.onCreate()
        val instance = LocalBroadcastManager.getInstance(this)

        scope.launch {
            val jsonObject = JSONObject(LOCATION)
            val features = jsonObject.get("features") as JSONArray
            for (i in 0 until features.length()) {
                val feature = features[i] as JSONObject
                val geometry = feature.get("geometry") as JSONObject
                val jsonArray = geometry.get("coordinates") as JSONArray
                val lat = jsonArray.getDouble(1)
                val lon = jsonArray.getDouble(0)
                println("Lat: $lat : Lon: $lon")
                val intent = Intent("location-update").apply {
                    putExtra("lat", lat)
                    putExtra("lon", lon)
                }
                instance.sendBroadcast(intent)
                delay(TimeUnit.SECONDS.toMillis(30))
            }
        }
    }

    override fun onTerminate() {
        scope.cancel()
        super.onTerminate()
    }
}