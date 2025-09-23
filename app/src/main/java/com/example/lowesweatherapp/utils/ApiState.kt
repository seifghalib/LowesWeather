package com.example.lowesweatherapp.utils

sealed class ApiState<out T> {

    data object EmptyState : ApiState<Nothing>()
    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val msg:String) : ApiState<Nothing>()
    data object Loading : ApiState<Nothing>()
}