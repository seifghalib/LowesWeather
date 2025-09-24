package com.example.lowesweatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lowesweatherapp.data.ApiResponse
import com.example.lowesweatherapp.data.Details
import com.example.lowesweatherapp.utils.ApiState
import com.example.lowesweatherapp.utils.Screens
import com.example.lowesweatherapp.viewmodels.MainViewModel
import kotlin.math.roundToInt


@Composable
fun MainView(
    viewModel: MainViewModel,
    modifier: Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            singleLine = true,
            label = {
                Text("City Name")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(
            modifier = modifier.size(200.dp, 50.dp),
            onClick = {
                keyboardController?.hide()
                viewModel.onTextChange(text)
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "LookUp",
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(
    modifier: Modifier,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val lazyState = rememberLazyListState()
    val response by viewModel.forecast
    val state = response.takeIf { it is ApiState.Success<ApiResponse> }

    state?.let { resp ->

        val success = resp as ApiState.Success
        val list = success.data.list
        val cityName = success.data.city.name

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = cityName) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }) { innerPadding ->

            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .background(Color.LightGray)
                    .fillMaxSize(),
                state = lazyState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = list
                ) { index, item ->
                    ListItem(
                        item = cityName to item,
                        navController = navController
                    )

                    if (index < list.lastIndex) {
                        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    modifier: Modifier,
    viewModel: MainViewModel,
    listItem: Pair<String, Details>,
    navController: NavHostController
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = listItem.first) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(innerPadding)
                .padding(
                    top = 32.dp,
                    start = 16.dp,
                    end = 24.dp
                )
        ) {

            val (temp, feelsLike) = listItem.second.main
            val (_, main, description, _) = listItem.second.weather.first()

            Text(
                modifier = modifier.fillMaxWidth(),
                text = "${temp.roundToInt()} \u2109",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Feels Like : ${feelsLike.roundToInt()} ℉",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = main,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
            )
        }

    }
}

@Composable
fun ListItem(
    item: Pair<String, Details>,
    navController: NavHostController
) {
    val listItem = item.second

    val right = "${listItem.main.temp.roundToInt()} \u2109"
    val left = listItem.weather.first().main

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        key = "forecast",
                        value = item
                    )

                navController.navigate(Screens.DetailScreen.route)
            },
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = left,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(1f),
            text = right,
            textAlign = TextAlign.Center
        )
    }
}