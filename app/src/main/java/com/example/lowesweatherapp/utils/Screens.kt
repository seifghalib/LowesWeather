package com.example.lowesweatherapp.utils

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home_screen")
    data object ListScreen : Screens("list_screen")
    data object DetailScreen : Screens("detail_screen")
}