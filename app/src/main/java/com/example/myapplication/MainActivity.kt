package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        connect_btn.setOnClickListener() {

            val parent = findViewById(R.id.server_list) as LinearLayout
            val b = Button(this)
            b.text = enter_text.text
            enter_text.setText("")
            b.setOnClickListener(){
                enter_text.setText(b.text)
            }
            parent.addView(b)
        }*/

        connect_btn.setOnClickListener(){
            // connect to url
            val url = enter_text.text
            //connect

            val intent = Intent(this, FlightActivity::class.java)
            startActivity(intent)
        }

    }

}