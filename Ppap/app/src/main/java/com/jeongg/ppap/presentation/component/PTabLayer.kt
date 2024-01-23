package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.theme.main_yellow


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PTabLayer(
    tabs: List<SubscribeGetResponseDTO>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    contents: LazyListScope.() -> Unit,
){
    val pagerState = rememberPagerState { tabs.size }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            onTabClick(pagerState.currentPage)
        }
    }
    Column {
        TopSubscribeList(selectedTabIndex, tabs, onTabClick)
        HorizontalPager(
            state = pagerState,
            pageSpacing = 15.dp,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            LazyColumn{
                item { PDivider(modifier = Modifier.padding(horizontal = 20.dp)) }
                contents()
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
        divider = {},
        modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth()
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
    TabRowDefaults.Indicator(
        modifier = Modifier.tabIndicatorOffset(
            currentTabPosition = tabPositionList[selectedTabIndex]
        ),
        color = main_yellow,
    )
}