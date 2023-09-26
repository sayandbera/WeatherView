package com.sayanx.weatherview.screens

/*
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sayanx.weatherview.R
import com.sayanx.weatherview.navigation.Destinations
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val scale = remember { Animatable(initialValue = 0f) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(
            key1 = true,
            block = {
                scale.animateTo(
                    targetValue = 0.8f,
                    animationSpec = tween(
                        durationMillis = 900,
                        easing = {
                            OvershootInterpolator(4f).getInterpolation(it)
                        }
                    )
                )
                delay(1500L)
                navController.navigate(Destinations.HomeScreen.route) {
                    navController.popBackStack(
                        route = Destinations.SplashScreen.route,
                        inclusive = true
                    )
                }
            }
        )

        Surface(
            modifier = Modifier
                .padding(15.dp)
                .size(250.dp)
                .scale(scale.value),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sun_cloud),
                    contentDescription = "Splash Icon",
                    modifier = Modifier.size(99.dp)
                )
                Text(
                    text = "Find the Sun?",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFA52800),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
*/