package com.example.memesmania

import android.telecom.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/gimme")
    fun getData():retrofit2.Call<responseModel>
}