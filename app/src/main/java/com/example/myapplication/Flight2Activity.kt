package com.example.myapplication

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.controlwear.virtual.joystick.android.JoystickView
import kotlinx.android.synthetic.main.controller.*
import kotlinx.android.synthetic.main.joystick.*
import kotlin.math.cos
import kotlin.math.sin

class Flight2Activity : AppCompatActivity() {
    var throttle = 0;
    var rudder = 0;
    var aileron = 0;
    var elevator = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight2)

        setBarListeners()
        setJoystickListeners()
    }

    private fun setBarListeners() {
        // Set a SeekBar change listener
        rudder_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                rudder_text.text = (i / 100.0).toString();
                if (changedEnough(i, rudder)) {
                    rudder = i
                    send();
                }
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
                throttle_text.text = (i / 100.0).toString();
                if (changedEnough(i, throttle)) {
                    throttle = i
                    send();
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })
    }

    private fun setJoystickListeners() {

/*        joystick.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_UP -> {
                    v.performClick()
                    println("up")
                }
            }
            false
        }*/

        joystick.setOnMoveListener(JoystickView.OnMoveListener(){angle: Int, strength: Int ->
            val length = strength;
            val x = length * cos(Math.toRadians(angle*1.0))
            val y = length * sin(Math.toRadians(angle*1.0))
            if(changedEnough(x.toInt(),aileron) || changedEnough(y.toInt(),elevator)) {
                aileron = x.toInt();
                elevator = y.toInt();
                send();
            }
        })

    }

    private fun changedEnough(a: Int, b: Int): Boolean {
        if (Math.abs(a - b) >= 1) {
            return true
        }
        return false
    }

    private fun send() {
        println("throt:$throttle, rud:$rudder ailer:$aileron elev:$elevator")
        //div by 100 and send to our server
        val commandBody = Command(aileron/100.0,rudder/100.0,elevator/100.0,throttle/100.0)
        val url = "@@@@@put url here@@@@@"
    }

    class Command(aileron:Double,rudder:Double,elevator:Double,throttle:Double){
    }
}
