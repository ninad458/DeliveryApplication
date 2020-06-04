package com.enigma.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.admin.activity_maps.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline

class MapActivity : BaseActivity() {

    private val locations: MutableList<Location> = mutableListOf()

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val lat = intent.getDoubleExtra("lat", 0.0)
            val lon = intent.getDoubleExtra("lon", 0.0)
            locations.add(Location(lat, lon))
            drawLine()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContentView(R.layout.activity_maps)

        listenToEvents()
        setMap()
    }

    private fun listenToEvents() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            messageReceiver, IntentFilter("location-update")
        )
    }

    private fun setMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        val mapController = map.controller
        mapController.setZoom(18.5)
        map.setMultiTouchControls(true)
    }

    private fun drawLine() {
        launch {
            val line = Polyline(map)
            val geoPoints = locations.map { GeoPoint(it.latitude, it.longitude) }
            line.setPoints(geoPoints)
            line.isGeodesic = true
            map.overlayManager.clear()
            map.overlayManager.add(line)
            if (geoPoints.isNotEmpty())
                map.controller.setCenter(geoPoints.last())
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
        super.onDestroy()
    }
}