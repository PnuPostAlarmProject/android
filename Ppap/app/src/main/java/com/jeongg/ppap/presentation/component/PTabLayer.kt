package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jeongg.ppap.data.dto.NoticeItemDTO
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.component.loading.PCircularProgress
import com.jeongg.ppap.presentation.component.loading.PSwipeRefreshIndicator
import com.jeongg.ppap.presentation.state.NoticeItemState
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.theme.main_yellow
import kotlinx.coroutines.launch

@Composable
fun PTabLayer(
    state: NoticeItemState,
    selectedTabIndex: Int,
    onNavigate: () -> Unit = {},
    onTabClick: (Int, Long) -> Unit,
) {
    val subscribes = state.subscribes
    val contents = state.contents.collectAsLazyPagingItems()

    if (state.isLoading){
        PCircularProgress()
    }
    else if (state.errorMessage.isNotEmpty()){
        PEmptyContent(message = state.errorMessage)
    }
    else if (subscribes.isEmpty()){
        PEmptyContentWithButton(onClick = onNavigate)
    }
    else {
        PTabLayerContent(
            onTabClick = onTabClick,
            subscribes = subscribes,
            selectedTabIndex = selectedTabIndex,
            contents = contents
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PTabLayerContent(
    onTabClick: (Int, Long) -> Unit,
    subscribes: List<SubscribeGetResponseDTO>,
    selectedTabIndex: Int,
    contents: LazyPagingItems<NoticeItemDTO>
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { subscribes.size }
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            val index = pagerState.currentPage
            if (index < subscribes.size)
                onTabClick(index, subscribes[index].subscribeId)
        }
    }
    Column {
        TopSubscribeList(
            selectedTabIndex = selectedTabIndex,
            tabs = subscribes,
            onTabClick = { index ->
                if (index < subscribes.size) {
                    onTabClick(index, subscribes[index].subscribeId)
                    scope.launch { pagerState.scrollToPage(index) }
                }
            }
        )
        SwipeRefresh(
            state = refreshState,
            onRefresh = { contents.refresh() },
            indicator = { state, trigger -> PSwipeRefreshIndicator(state, trigger) }
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 15.dp,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { page ->
                if (page == selectedTabIndex &&
                    contents.loadState.refresh !is LoadState.Loading
                ) {
                    HorizontalPagerContent(contents)
                }
            }
        }
    }
}

@Composable
private fun HorizontalPagerContent(
    contents: LazyPagingItems<NoticeItemDTO>
) {
    if (contents.itemCount == 0) {
        PEmptyContent(message = "아직 등록된 게시글이 없어요.")
    }
    else {
        LazyColumn {
            items(
                count = contents.itemCount,
                key = contents.itemKey { it.contentId }
            ) { index ->
                NoticeItem(noticeItemDTO = contents[index] ?: NoticeItemDTO())
            }
        }
    }
}

@Composable
private fun TopSubscribeList(
    selectedTabIndex: Int,
    tabs: List<SubscribeGetResponseDTO>,
    onTabClick: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.White,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.background,
        indicator = { TabLayerIndicator(it, selectedTabIndex) },
        divider = { PDivider() },
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, value ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabClick(index) },
                text = { TabText(value.title) },
                interactionSource = NoRippleInteractionSource,
                selectedContentColor = main_yellow,
                unselectedContentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun TabText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
private fun TabLayerIndicator(
    tabPositionList: List<TabPosition>,
    selectedTabIndex: Int
) {
    SecondaryIndicator(
        modifier = Modifier.tabIndicatorOffset(
            currentTabPosition = tabPositionList[selectedTabIndex]
        ),
        color = main_yellow
    )
}