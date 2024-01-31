package com.jeongg.ppap.presentation.scrap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongg.ppap.presentation.component.ExitBackHandler
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.noticeItem.noticeItemContent

@Composable
fun ScrapScreen(
    navController: NavController,
    viewModel: ScrapViewModel = hiltViewModel()
){
    val selectedTabIndex = rememberSaveable { mutableIntStateOf(0) }
    val contents = viewModel.contents.collectAsLazyPagingItems()
    val subscribeList = viewModel.subscribes.value

    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    ExitBackHandler()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrapTitle()
        if (viewModel.isSubscribeListEmpty()) {
            PEmptyContent(
                onClick = { navController.navigate(Screen.SubscribeCustomAddScreen.route) }
            )
            return
        }
        PTabLayer(
            tabs = subscribeList,
            selectedTabIndex = selectedTabIndex.intValue,
            onTabClick = { tabIndex ->
                if (selectedTabIndex.intValue != tabIndex) {
                    selectedTabIndex.intValue = tabIndex
                    viewModel.getScrapPage(subscribeList[tabIndex].subscribeId)
                }
            },
            contents = { noticeItemContent(contents = contents) }
        )
    }
}

@Composable
private fun ScrapTitle() {
    Text(
        text = "스크랩",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
    )
}