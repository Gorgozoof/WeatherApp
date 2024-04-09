package com.example.pogoda_app

import android.util.Log
import android.widget.ImageView
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.pogoda_app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val temperatureTextView = findViewById<TextView>(R.id.temperatureTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val windSpeedTextView = findViewById<TextView>(R.id.windSpeedTextView)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val cityEditText = findViewById<EditText>(R.id.cityEditText)
        val getWeatherButton = findViewById<Button>(R.id.getWeatherButton)

        getWeatherButton.setOnClickListener {
            val cityName = cityEditText.text.toString()
            if (cityName.isNotEmpty()) {
                val weatherApi = WeatherApi()
                weatherApi.getWeather(cityName, "a5f9f575af0280f1938b64b89bbd92d5") { weatherResponse: WeatherResponse?, error: Throwable? ->
                    runOnUiThread {
                        if (weatherResponse != null) {
                            val temperature = weatherResponse.main.temp
                            val description = weatherResponse.weather.firstOrNull()?.description ?: ""
                            val windSpeed = weatherResponse.wind.speed

                            temperatureTextView.text = "Temperatura: $temperature°C"
                            descriptionTextView.text = "Opis: $description"
                            windSpeedTextView.text = "Wiatr: $windSpeed m/s"

                            updateWeatherIcon(description, imageView)
                        } else {
                            temperatureTextView.text = "Błąd podczas pobierania danych pogodowych dla miasta: $cityName."
                            error?.printStackTrace()
                        }
                    }
                }
            } else {
                cityEditText.error = "Proszę wpisać nazwę miasta."
            }
        }
    }


    fun updateWeatherIcon(description: String, imageView: ImageView) {
        Log.d("WeatherApp", "Pogoda opis: $description")
        val resourceId = when {
            description.contains("clear", ignoreCase = true) -> R.drawable.sunny
            description.contains("rain", ignoreCase = true) -> R.drawable.rainy
            description.contains("cloud", ignoreCase = true) -> R.drawable.cloudy
            else -> R.drawable.undefined
        }
        imageView.setImageResource(resourceId)
    }

}
