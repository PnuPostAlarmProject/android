package com.jeongg.ppap.presentation.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jeongg.ppap.R
import com.jeongg.ppap.data.dto.NoticeItemDTO
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.bright_yellow


@Composable
fun NoticeListScreen(
    navController: NavController,
    viewModel: NoticeViewModel = hiltViewModel()
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val contents = viewModel.contents.collectAsLazyPagingItems()
    val subscribeList = viewModel.subscribes.value

    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        NoticeListTitle()
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
                viewModel.getNoticePage(subscribeList[tabIndex].subscribeId)
            }
        )
    }
}

@Composable
private fun NoticeListTitle() {
    Row(
        modifier = Modifier
            .background(bright_yellow)
            .fillMaxWidth()
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.name_white),
            contentDescription = "ppap logo",
            modifier = Modifier.padding(start = 20.dp, end = 7.dp).width(46.dp)
        )
        Image(
            painter = painterResource(R.drawable.apple),
            contentDescription = "apple character",
            modifier = Modifier.fillMaxHeight()
        )
    }
}