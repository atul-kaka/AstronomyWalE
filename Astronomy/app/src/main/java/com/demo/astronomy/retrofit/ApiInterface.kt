package com.demo.astronomy.retrofit

import com.demo.astronomy.model.APODModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("planetary/apod?api_key=ONMSbO5jbWB1YlL9Ly5M0HTPjzp541IVP9ScEECp")
    fun getAPODData(): Call<APODModel>
}