package com.mahmoudhamdyae.dailyforecast.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel
import com.mahmoudhamdyae.dailyforecast.domain.model.toDate
import com.mahmoudhamdyae.dailyforecast.ui.theme.LocalSpacing
import com.mahmoudhamdyae.dailyforecast.util.TestTags

@Composable
fun WeatherData(
    weather: List<WeatherModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = weather.first().cityName,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        WeatherCard(weather = weather.first())
        WeatherList(weather = weather)
    }
}

@Composable
fun WeatherCard(
    weather: WeatherModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(LocalSpacing.current.small),
        modifier = modifier
            .testTag(TestTags.WEATHER_CARD)
            .padding(LocalSpacing.current.medium)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = weather.date.toDate(),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = weather.time,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            Text(
                text = "${weather.temp}°C",
                fontSize = 50.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
            Row {
                Text(
                    text = weather.weather,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Wind Speed: ${weather.windSpeed}",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun WeatherList(
    weather: List<WeatherModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        LazyRow(content = {
            items(weather) { weather ->
                WeatherListItem(
                    weather = weather,
                )
            }
        })
    }
}

@Composable
fun WeatherListItem(
    weather: WeatherModel,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Card(
            shape = RoundedCornerShape(LocalSpacing.current.small),
            modifier = modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(LocalSpacing.current.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = weather.date.toDate(),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.small))
                Text(
                    text = weather.time,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.small))
                Text(
                    text = "${weather.temp}°C",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(LocalSpacing.current.small))
    }
}