package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class FlightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_flight)
        }
         if (resources.configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_flight_land)
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            //landscape
            setContentView(R.layout.activity_flight_land)
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_flight)
        }
    }
}