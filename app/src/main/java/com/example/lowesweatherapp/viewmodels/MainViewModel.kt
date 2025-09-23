package com.example.lowesweatherapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lowesweatherapp.data.ApiResponse
import com.example.lowesweatherapp.di.WeatherRepository
import com.example.lowesweatherapp.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _forecast: MutableState<ApiState<ApiResponse>> = mutableStateOf(ApiState.EmptyState)
    val forecast: State<ApiState<ApiResponse>> = _forecast

    fun onTextChange(query: String) {

        _forecast.value = ApiState.Loading

        viewModelScope.launch {
            weatherRepository.getWeather(query)
                .fold(
                    onSuccess = { response ->
                        _forecast.value = ApiState.Success(response)
                    },
                    onFailure = { err ->
                        _forecast.value = ApiState.Failure(err.message ?: "Unknown Error")
                    }
                )
        }
    }
}