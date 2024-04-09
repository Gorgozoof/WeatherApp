package com.example.pogoda_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApi {
    private val baseUrl = "https://api.openweathermap.org/"

    private val api: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    fun getWeather(city: String, apiKey: String, callback: (WeatherResponse?, Throwable?) -> Unit) {
        api.getWeather(city, "metric", apiKey).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(call: retrofit2.Call<WeatherResponse>, response: retrofit2.Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, RuntimeException("API call successful but wrong response"))
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}
