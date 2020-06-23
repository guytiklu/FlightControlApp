package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.*
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
            applicationContext,
            adressAndTimeDatabase::class.java,
            "url Database"
        ).build()
        createButtonsListeners(db)
        readFromDataBase(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createButtonsListeners(database: adressAndTimeDatabase) {
        btn1.setOnClickListener {
            if (btn1.text.equals("1")) {
                return@setOnClickListener
            }
            enter_text.setText(btn1.text.toString())
        }
        btn2.setOnClickListener {
            if (btn2.text.equals("2")) {
                return@setOnClickListener
            }
            enter_text.setText(btn2.text.toString())
        }
        btn3.setOnClickListener {
            if (btn3.text.equals("3")) {
                return@setOnClickListener
            }
            enter_text.setText(btn3.text.toString())
        }
        btn4.setOnClickListener {
            if (btn4.text.equals("4")) {
                return@setOnClickListener
            }
            enter_text.setText(btn4.text.toString())
        }
        btn5.setOnClickListener {
            if (btn5.text.equals("5")) {
                return@setOnClickListener
            }
            enter_text.setText(btn5.text.toString())
        }
        createConnectBtn(database)
    }

    private fun readFromDataBase(db: adressAndTimeDatabase) { ///getting all the urls from the data base and putting them in list
        CoroutineScope(IO).launch {
            val list = db.getAdressAndTimeDao().getAllUrls()
            if (list.isEmpty()) { //if the list is empty
                return@launch;
            }
            putButtonsCorrect(list) // we will put the data base correctly in the buttons
        }
    }

    private fun insertData(adressAndTime: adressAndTime, db: adressAndTimeDatabase) {
        CoroutineScope(IO).launch {
            db.getAdressAndTimeDao().updateNumbers()
            db.getAdressAndTimeDao().addUrl(adressAndTime)
            readFromDataBase(db)
        }
    }

    private suspend fun putButtonsCorrect(list: List<adressAndTime>) {
        withContext(Main) {
            if (list.size == 1) {
                btn1.text = list[0].adress
            } else if (list.size == 2) {
                btn1.text = list[0].adress
                btn2.text = list[1].adress
            } else if (list.size == 3) {
                btn1.text = list[0].adress
                btn2.text = list[1].adress
                btn3.text = list[2].adress
            } else if (list.size == 4) {
                btn1.text = list[0].adress
                btn2.text = list[1].adress
                btn3.text = list[2].adress
                btn4.text = list[3].adress
            } else if (list.size >= 5) {
                btn1.text = list[0].adress
                btn2.text = list[1].adress
                btn3.text = list[2].adress
                btn4.text = list[3].adress
                btn5.text = list[4].adress
            }
        }
    }

    private fun createConnectBtn(database: adressAndTimeDatabase) {
        connect_btn.setOnClickListener {
            val urlText = enter_text.text.toString()
            // now we need to add the url to the db and set the time for the LRU
            val url = adressAndTime(urlText)
            insertData(url, database)

            val intent = Intent(this, Flight3Activity::class.java)
            intent.putExtra("url", urlText)

            try {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val retrofit = Retrofit.Builder()
                    .baseUrl(urlText)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                val api = retrofit.create(FlyService::class.java)
                // Getting the picture
                val body = api.getScreenshot().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>, response: Response<ResponseBody>
                    ) {
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(applicationContext, "Bad Communication", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
            catch(e:Exception){
                Toast.makeText(applicationContext, "Bad URL", Toast.LENGTH_SHORT)
                    .show()
                println(e.message)
            }


        }
    }

}
