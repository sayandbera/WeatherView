package com.sayanx.weatherview.screens.favourite

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sayanx.weatherview.R
import com.sayanx.weatherview.model.room.Favourite
import com.sayanx.weatherview.navigation.Destinations
import com.sayanx.weatherview.util.AppColor

@Composable
fun FavouriteScreen(
    navController: NavController,
    favouriteViewModel: FavouriteViewModel
) {
    var cityNo = 1
    val favList = favouriteViewModel.favList.collectAsState().value
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp)) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 6.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(items = favList) {
                Surface(
                    color = Color(0xFFFBEEFF),
                    border = BorderStroke(0.5.dp, AppColor.elBlue),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(30.dp))
                        .height(70.dp)
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable {
                            navController.navigate(Destinations.HomeScreen.passCity(it.city))
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                color = AppColor.gray,
                                fontSize = 14.sp,
                                text = "$cityNo.",
                                modifier = Modifier.padding(end = 8.dp),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = it.city,
                                modifier = Modifier.padding(3.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = it.country,
                            modifier = Modifier.padding(3.dp),
                            fontWeight = FontWeight.SemiBold
                        )

                        IconButton(
                            onClick = {
                                favouriteViewModel.deleteFav(Favourite(it.city, it.country))
                                Toast.makeText(context, "Removed from Favourite List", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                tint = AppColor.gray,
                                painter = painterResource(id = R.drawable.delete_outline),
                                contentDescription = "Delete City"
                            )
                        }
                    }
                }
                cityNo++
            }
        }
    }
}

