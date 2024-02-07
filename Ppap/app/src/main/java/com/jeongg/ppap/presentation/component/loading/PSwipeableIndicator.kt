package com.jeongg.ppap.presentation.component.loading

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.jeongg.ppap.theme.main_yellow

@Composable
fun PSwipeRefreshIndicator(
    state: SwipeRefreshState,
    refreshTrigger: Dp
) {
    SwipeRefreshIndicator(
        state = state,
        refreshTriggerDistance = refreshTrigger,
        backgroundColor = Color.White,
        contentColor = main_yellow
    )
}