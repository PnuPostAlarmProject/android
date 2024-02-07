package com.jeongg.ppap.presentation.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.util.ExitBackHandler
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.navigation.Screen

@Composable
fun NoticeListScreen(
    navController: NavController,
    viewModel: NoticeViewModel = hiltViewModel()
){
    val selectedTabIndex = rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    ExitBackHandler()
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        NoticeListTitle()
        PTabLayer(
            state = viewModel.state.value,
            selectedTabIndex = selectedTabIndex.intValue,
            onNavigate = { navController.navigate(Screen.SubscribeScreen.route) },
            onTabClick = { tabIndex, subscribeId ->
                if (selectedTabIndex.intValue != tabIndex){
                    selectedTabIndex.intValue = tabIndex
                    viewModel.getNoticePage(subscribeId)
                }
            }
        )
    }
}

@Composable
private fun NoticeListTitle() {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.name_yellow),
            contentDescription = "ppap logo",
            modifier = Modifier
                .padding(start = 20.dp, end = 7.dp)
                .width(60.dp)
        )
        Image(
            painter = painterResource(R.drawable.apple),
            contentDescription = "apple character",
            modifier = Modifier.fillMaxHeight()
        )
    }
}