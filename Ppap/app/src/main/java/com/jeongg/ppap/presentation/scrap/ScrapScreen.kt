package com.jeongg.ppap.presentation.scrap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.notice.noticeItemContent

@Composable
fun ScrapScreen(
    navController: NavController,
    viewModel: ScrapViewModel = hiltViewModel()
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val contents = viewModel.contents.collectAsLazyPagingItems()
    val subscribeList = viewModel.subscribes.value

    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "스크랩",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )
        if (viewModel.isEmpty()) {
            PEmptyContent(
                onClick = { navController.navigate(Screen.SubscribeAddScreen.route) }
            )
            return
        }
        PTabLayer(
            tabs = subscribeList,
            selectedTabIndex = selectedTabIndex,
            contents = { noticeItemContent(contents = contents) },
            onTabClick = { tabIndex ->
                selectedTabIndex = tabIndex
                viewModel.getScrapPage(subscribeList[tabIndex].subscribeId)
            }
        )
    }
}