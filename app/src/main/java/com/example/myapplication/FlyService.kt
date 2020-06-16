package com.example.myapplication

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FlyService {
    @GET("/screenshot")
    fun getScreenshot() :retrofit2.Call<ResponseBody>

    @POST("/api/command")
    fun sendCommand(@Body data:Command):retrofit2.Call<ResponseBody>
}