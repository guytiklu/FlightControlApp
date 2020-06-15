package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.controller.*


class FlightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_flight)
        }
        else if (resources.configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_flight_land)
        }*/
        setContentView(R.layout.activity_flight)
        setBarListeners()
    }

/*    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
*//*        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_flight_land)
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_flight)
        }
        setBarListeners()*//*
    }*/

    private fun setBarListeners(){
        // Set a SeekBar change listener
        rudder_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                rudder_text.text = (i/100.0).toString();
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })

        // Set a SeekBar change listener
        throttle_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                throttle_text.text = (i/100.0).toString();
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })
    }

}