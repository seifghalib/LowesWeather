package com.example.lowesweatherapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lowesweatherapp.data.Details
import com.example.lowesweatherapp.utils.Screens
import com.example.lowesweatherapp.viewmodels.MainViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(
            route = Screens.HomeScreen.route
        ) {

            MainView(
                viewModel = viewModel,
                modifier = modifier
            )
        }

        composable(
            route = Screens.ListScreen.route
        ) {
            ListView(
                modifier = modifier,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screens.DetailScreen.route
        ) {
            val listItem = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Pair<String, Details>>(key = "forecast")

            listItem?.let {
                DetailView(
                    modifier = modifier,
                    viewModel = viewModel,
                    listItem = it,
                    navController = navController
                )
            }
        }
    }

}