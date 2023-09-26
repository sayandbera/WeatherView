package com.sayanx.weatherview.componenets

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayanx.weatherview.util.AppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: String,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = AppColor.elBlue,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .heightIn(max = 45.dp)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                actionIconContentColor = Color(0xFF007BFF)
            ),
            navigationIcon = {
                IconButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { scope.launch { drawerState.open() } }
                ) {
                    Icon(
                        tint = Color.Black,
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Drawer Icon"
                    )
                }
            },
            title = {
                Text(
                    text = title,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.2.sp
                    )
                )
            },
            actions = {}
        )
    }
}