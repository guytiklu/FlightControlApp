package com.example.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import io.github.controlwear.virtual.joystick.android.JoystickView
import kotlinx.android.synthetic.main.controller.*
import kotlinx.android.synthetic.main.image.*
import kotlinx.android.synthetic.main.joystick.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.cos
import kotlin.math.sin

class Flight3Activity : AppCompatActivity() {
    var url = "http://10.0.2.2:12345";
    var throttle = 0;
    var rudder = 0;
    var aileron = 0;
    var elevator = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight3)

        url = intent.getStringExtra("url").toString()
        setBarListeners()
        setJoystickListeners()
        getImgLoop()
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

        joystick.setOnMoveListener(JoystickView.OnMoveListener() { angle: Int, strength: Int ->
            val length = strength;
            val x = length * cos(Math.toRadians(angle * 1.0))
            val y = length * sin(Math.toRadians(angle * 1.0))
            if (changedEnough(x.toInt(), aileron) || changedEnough(y.toInt(), elevator)) {
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
        val command =
            Command(aileron / 100.0, rudder / 100.0, elevator / 100.0, throttle / 100.0)

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url.toString())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(FlyService::class.java)
        val body = api.sendCommand(command).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //Log.d("test", "onResponse")
                if (response.code() !in 400..598) { // we got a valid code
                } else { // Server returned error code
                    val str =
                        "Server Error: " + response.code().toString() + ", " + response.message()
                    println(str)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //Log.d("test", "onFailKaki")
                Toast.makeText(applicationContext, "Server Failure", Toast.LENGTH_SHORT).show()
                println(t.message)
            }
        })
    }

    private fun getImgLoop() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val api = retrofit.create(FlyService::class.java)
        // Getting the picture
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(250)
                val body = api.getScreenshot().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>
                    ) {
                        val bytes = response?.body()?.bytes()
                        val bitmap =
                            bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
                        if (bitmap != null) {
                            img.setImageBitmap(bitmap)
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Failed to get image", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }
}

