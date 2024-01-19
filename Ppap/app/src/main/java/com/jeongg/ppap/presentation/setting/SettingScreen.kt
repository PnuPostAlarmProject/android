package com.jeongg.ppap.presentation.setting

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PTitle
import com.jeongg.ppap.presentation.component.negativePadding
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.bright_pink
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingScreen(
    navController: NavController,
    onUpPress: () -> Unit = {},
    viewModel: SettingViewModel = hiltViewModel()
){
    val context = LocalContext.current
    var isDialogOpen by remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PEvent.SUCCESS -> { navController.navigate(Screen.LoginScreen.route) }
                is PEvent.ERROR -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { "로딩중".log() }
            }
        }
    }
    if (isDialogOpen > 0) {
        Dialog(
            onDismissRequest = { isDialogOpen = 0 }
        ) {
            val text =  if(isDialogOpen == 1) "로그아웃 하시겠습니까?" else "정말로 회원탈퇴를 계속하시겠습니까?"
            val okText =  if(isDialogOpen == 1) "로그아웃" else "회원탈퇴"
            PDialog(
                text = text,
                cancelText = "취소하기",
                okText = okText,
                onDeleteClick = { isDialogOpen = 0 },
                onEditClick = {
                    if (isDialogOpen == 1) viewModel.logout()
                    else viewModel.withdrawl()
                },
                isSubscribe = false
            )
        }
    }
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
                    onSubscribeAdd = {navController.navigate(Screen.SubscribeAddScreen.route)},
                    onScrap = {navController.navigate(Screen.ScrapScreen.route)}
                )
            }
            item {
                MyPageScreen(
                    onLogOut = {isDialogOpen = 1},
                    onWithdraw = {isDialogOpen = 2}
                )
            }
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
fun MyPageScreen(
    onLogOut: () -> Unit = {},
    onWithdraw: () -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Text(
            text = stringResource(R.string.mypage),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
        )
        SettingItem(
            text = stringResource(R.string.logout),
            onClick = onLogOut
        )
        SettingItem(
            text = stringResource(R.string.leave_app),
            onClick = onWithdraw
        )
    }
}

@Composable
fun ServiceScreen(
    onSubscribe: () -> Unit = {},
    onSubscribeAdd: () -> Unit = {},
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
        SettingItem(stringResource(R.string.subscribe_get), onSubscribe)
        SettingItem(stringResource(R.string.subscribe_add), onSubscribeAdd)
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
            painter = painterResource(R.drawable.apple),
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


