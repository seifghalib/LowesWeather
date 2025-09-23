package com.example.lowesweatherapp.api

import com.example.lowesweatherapp.BuildConfig
import com.example.lowesweatherapp.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val OPEN_CLIENT_ID = BuildConfig.OPENWEATHER_API_KEY
    }

    @GET("forecast")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appid") appid: String = OPEN_CLIENT_ID,
        @Query("units") units : String = "imperial"
    ): Response<ApiResponse>
}