package com.example.lowesweatherapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ApiResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<Details>,
    val city: City,
)
@Parcelize
data class Details(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Long,
    val pop: Double,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val rain: Rain?,
): Parcelable

@Parcelize
data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
) : Parcelable

@Parcelize
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
) : Parcelable

@Parcelize
data class Clouds(
    val all: Long,
) : Parcelable

@Parcelize
data class Wind(
    val speed: Double,
    val deg: Long,
    val gust: Double,
): Parcelable

@Parcelize
data class Sys(
    val pod: String,
): Parcelable

@Parcelize
data class Rain(
    @SerializedName("3h")
    val n3h: Double,
): Parcelable

data class City(
    val id: Long,
    val name: String,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long,
)
