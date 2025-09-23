package com.example.lowesweatherapp.di

import com.example.lowesweatherapp.api.WeatherApi
import com.example.lowesweatherapp.data.ApiResponse
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class WeatherRepository @Inject constructor(private val openWeatherApi: WeatherApi) {

    suspend fun getWeather(query: String): Result<ApiResponse> = runCatching {
        openWeatherApi.getWeather(query)
    }.mapCatching { resp ->
        val response = if (resp.isSuccessful) {
            resp.body()
        } else {
            resp.errorBody().use { err ->
                error(err?.string() ?: "Unknow Error")
            }
        }
        requireNotNull(response)
    }
}