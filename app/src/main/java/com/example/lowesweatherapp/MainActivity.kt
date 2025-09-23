package com.example.lowesweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lowesweatherapp.screens.SetupNavGraph
import com.example.lowesweatherapp.ui.theme.LowesWeatherAppTheme
import com.example.lowesweatherapp.utils.ApiState
import com.example.lowesweatherapp.utils.Screens
import com.example.lowesweatherapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LowesWeatherAppTheme {

                val response by viewModel.forecast
                val state = response
                navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    when (state) {
                        is ApiState.Failure -> {
                            println("LIBU ${state.msg}")
                        }

                        ApiState.Loading -> CircularProgressIndicator()
                        is ApiState.Success -> {
                            navController.navigate(route = Screens.ListScreen.route)
                        }

                        ApiState.EmptyState -> {}
                    }
                }

            }
        }
    }
}