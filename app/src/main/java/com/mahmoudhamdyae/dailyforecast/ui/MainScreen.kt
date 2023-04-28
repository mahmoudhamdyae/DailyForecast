package com.mahmoudhamdyae.dailyforecast.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudhamdyae.dailyforecast.R
import com.mahmoudhamdyae.dailyforecast.ui.theme.LocalSpacing
import com.mahmoudhamdyae.dailyforecast.util.TestTags

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    var searchText by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(LocalSpacing.current.small)
        .verticalScroll(rememberScrollState())) {
        MainScreenHeader(
            searchText = searchText,
            onSearchValurChanged = { searchText = it },
            onSearch = viewModel::getWeather
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)) {
            if (uiState.isLoading) {
                LoadingScreen(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                ErrorScreen(
                    error = uiState.error,
                    retryAction = { viewModel.getWeather(searchText) },
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                MainScreenContent(uiState = uiState,modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenHeader(
    searchText: String,
    onSearchValurChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        .fillMaxWidth()
        .padding(bottom = LocalSpacing.current.small)
    ) {
        TextField(
            value = searchText,
            onValueChange = { onSearchValurChanged(it) },
            placeholder = { Text(text = stringResource(id = R.string.search_edit_text_label)) },
            trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchText)
            }),
            modifier = Modifier.weight(1f)
        )
        OutlinedIconButton(
            shape = CircleShape,
            onClick = {
                onSearch(searchText)
            }, modifier = Modifier.padding(start = LocalSpacing.current.small)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.search)
            )
        }
    }
}

@Composable
fun ErrorScreen(
    error: String,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = error,
            textAlign = TextAlign.Center,
            style = TextStyle(color = Color.Red),
            modifier = Modifier.padding(LocalSpacing.current.medium)
        )
        Button(onClick = { retryAction() }) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.testTag(TestTags.LOADING)
    )
}

@Composable
fun MainScreenContent(
    uiState: WeatherUiState,
    modifier: Modifier = Modifier
) {
    if (uiState.weather.isEmpty()) {
        EmptyData(modifier)
    } else {
        val isAccurateData by rememberSaveable {
            mutableStateOf(uiState.weather.any { it.isAccurateData })
        }
        Column(modifier = modifier) {
            if (!isAccurateData) {
                Box(modifier = Modifier
                    .testTag(TestTags.NOT_ACCURATE_DATA)
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(LocalSpacing.current.small)
                ) {
                    Text(
                        text = stringResource(id = R.string.not_accurate_data),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            WeatherData(
                weather = uiState.weather,
                modifier = Modifier.padding(top = LocalSpacing.current.small)
            )
        }
    }
}

@Composable
fun EmptyData(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = stringResource(id = R.string.empty_screen))
    }
}