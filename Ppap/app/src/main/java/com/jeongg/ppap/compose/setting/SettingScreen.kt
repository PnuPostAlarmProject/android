package com.jeongg.ppap.compose.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.compose.component.PTitle
import com.jeongg.ppap.compose.component.negativePadding
import com.jeongg.ppap.compose.navigation.Screen
import com.jeongg.ppap.compose.theme.Dimens
import com.jeongg.ppap.compose.theme.bright_pink

@Composable
fun SettingScreen(
    navController: NavController,
    onUpPress: () -> Unit = {}
){
    PTitle(
        title = stringResource(R.string.setting_title),
        onUpPress = onUpPress
    ){
        DescriptionScreen()
        LazyColumn(
            modifier = Modifier
                .negativePadding()
                .padding(top = 30.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = Dimens.PaddingLarge,
                        topEnd = Dimens.PaddingLarge
                    )
                )
                .background(bright_pink)
                .fillMaxSize()
        ){
            item {
                ServiceScreen(
                    onSubscribe = {navController.navigate(Screen.SubscribeScreen.route)},
                    onScrap = {navController.navigate(Screen.NoticeScrapScreen.route)}
                )
            }
            item { MyPageScreen() }
        }
    }
}
@Composable
fun SettingItem(
    text: String = "",
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 41.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Image(
            painter = painterResource(R.drawable.arrow),
            contentDescription = "navigation_arrow",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
@Composable
fun MyPageScreen() {
    Column(
        modifier = Modifier.padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Text(
            text = stringResource(R.string.mypage),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
        )
        SettingItem(stringResource(R.string.logout))
        SettingItem(stringResource(R.string.leave_app))
    }
}

@Composable
fun ServiceScreen(
    onSubscribe: () -> Unit = {},
    onScrap: () -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Text(
            text = stringResource(R.string.service),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
        )
        SettingItem(stringResource(R.string.subscribe_edit_add), onSubscribe)
        SettingItem(stringResource(R.string.scrap), onScrap)
        SettingItem(stringResource(R.string.version_open_source))
    }
}

@Composable
fun DescriptionScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.PaddingNormal),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(R.drawable.apple_no_background),
            contentDescription = "apple",
            modifier = Modifier
                .clip(CircleShape)
                .background(bright_pink)
                .padding(5.dp)
                .size(50.dp)
        )
        Text(
            text = stringResource(R.string.app_description2),
            style = MaterialTheme.typography.titleSmall
        )
    }
}


