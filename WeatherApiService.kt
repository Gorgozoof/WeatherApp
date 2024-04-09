package com.example.pogoda_app


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    fun getWeather(@Query("q") city: String, @Query("units") units: String, @Query("appid") apiKey: String): Call<WeatherResponse>
}
