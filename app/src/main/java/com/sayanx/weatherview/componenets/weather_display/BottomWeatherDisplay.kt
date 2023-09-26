package com.sayanx.weatherview.componenets.weather_display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayanx.weatherview.model.remote.Weather
import com.sayanx.weatherview.util.Utils
import com.sayanx.weatherview.util.WeatherStateImage

@Composable
fun BottomWeatherDisplay(weather: Weather) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFEEF4FF),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
/*
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(
//                        brush = Brush.linearGradient(listOf(Color(0xFFA5E3FF), Color(0xFF65E0FF)
//                        ))
//                    )
//            ) {
//                Text(
//                    text = "▼ 7 Days Forecast ▼",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.SemiBold
//                )
//            } */

            LazyColumn(
                modifier = Modifier.padding(horizontal = 6.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = weather.list) {day ->

                    val imgUrl = "https://openweathermap.org/img/wn/${day.weather[0].icon}.png"
                    Surface(
                        shape = RoundedCornerShape(20),
                        color = Color.White,
                        shadowElevation = 1.5.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = Utils.formateDate(day.dt).split(",")[0],
                                modifier = Modifier.padding(3.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                            WeatherStateImage(imgUrl = imgUrl)

                            Box(
                                modifier = Modifier
                                    .background(
                                        brush = Brush.linearGradient(listOf(
                                            Color(0xFFFFF7C1),
                                            Color(0xFFFFEF64)
                                        )),
                                        shape = RoundedCornerShape(50),
                                    )
                            ) {
                                Text(
                                    text = "  ${day.weather[0].description}  ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(3.dp),
                                    color = Color.Black
                                )
                            }

                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Red,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append("${Utils.formateDecimals(day.temp.max)}°")
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.Blue,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append("${Utils.formateDecimals(day.temp.min)}°")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}