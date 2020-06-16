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


class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("checkk !@!@")
        val db = Room.databaseBuilder(
            applicationContext,
            adressAndTimeDatabase::class.java,
            "url Database"
        ).build()
        println("im hereeeee")
        createButtons(db)
        readFromDataBase(db)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createButtons(database: adressAndTimeDatabase) {
        val button1 = findViewById(R.id.btn1) as Button
        button1.setOnClickListener {
            if (button1.text.equals("1")) {
                return@setOnClickListener
            }
            enter_text.setText(button1.text.toString())
        }
        val button2 = findViewById(R.id.btn2) as Button
        button2.setOnClickListener {
            if (button2.text.equals("2")) {
                return@setOnClickListener
            }
            enter_text.setText(button2.text.toString())
        }
        val button3 = findViewById(R.id.btn3) as Button
        button3.setOnClickListener {
            if (button3.text.equals("3")) {
                return@setOnClickListener
            }
            enter_text.setText(button3.text.toString())
        }
        val button4 = findViewById(R.id.btn4) as Button
        button4.setOnClickListener {
            if (button4.text.equals("4")) {
                return@setOnClickListener
            }
            enter_text.setText(button4.text.toString())
        }
        val button5 = findViewById(R.id.btn5) as Button
        button5.setOnClickListener {
            if (button5.text.equals("5")) {
                return@setOnClickListener
            }
            enter_text.setText(button5.text.toString())
        }
        createConnectBtn(database)

    }

    fun readFromDataBase(db: adressAndTimeDatabase) { ///getting all the urls from the data base and putting them in list
        CoroutineScope(IO).launch {
            val list = db.getAdressAndTimeDao().getAllUrls()
            if (list.isEmpty()) { //if the list is empty
                return@launch;
            }
            putButtonsCorrect(list) // we will put the data base correctly in the buttons
        }
    }

    fun insertData(adressAndTime: adressAndTime, db: adressAndTimeDatabase) {
        CoroutineScope(IO).launch {
            db.getAdressAndTimeDao().updateNumbers()
            db.getAdressAndTimeDao().addUrl(adressAndTime)
            readFromDataBase(db)
        }
    }

    suspend fun putButtonsCorrect(list: List<adressAndTime>) {
        withContext(Main) {
            val btn1 = findViewById(R.id.btn1) as Button
            val btn2 = findViewById(R.id.btn2) as Button
            val btn3 = findViewById(R.id.btn3) as Button
            val btn4 = findViewById(R.id.btn4) as Button
            val btn5 = findViewById(R.id.btn5) as Button
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

    fun createConnectBtn(database: adressAndTimeDatabase) {
        connect_btn.setOnClickListener {
            val urlText = enter_text.text.toString()
            // now we need to add the url to the db and set the time for the LRU
            val url = adressAndTime(urlText)
            insertData(url, database)

            //check connection to server
            if (checkConnection(urlText)) {
                val intent = Intent(this, Flight2Activity::class.java)
                intent.putExtra("url", urlText)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Bad URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkConnection(url: String): Boolean {
        /*try {
            var success = false
            val command = Command(0.0, 0.0, 0.0, 0.0)

            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val api = retrofit.create(FlyService::class.java)
            val body = api.sendCommand(command).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() !in 400..598) { // we got a valid code
                        success = true
                    } else { // Server returned error code
                        val str = "Server Error: " + response.code()
                            .toString() + ", " + response.message()
                        println(str)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, "Server Failure", Toast.LENGTH_SHORT).show()
                    println(t.message)
                }
            })

            return success
        } catch (e: Exception) {
            println("exception")
            return false
        }*/
        return true
    }
}
