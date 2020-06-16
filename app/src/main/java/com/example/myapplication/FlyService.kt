package com.example.myapplication

import android.telecom.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FlyService {
    @GET("screenshot")
    fun getScreenshot() :retrofit2.Call<String>

    @POST("api/command")
    fun sendCommand(@Body data:Command)
}