package com.jeongg.ppap.presentation.navigation

import androidx.annotation.DrawableRes
import com.jeongg.ppap.R

sealed class BottomNavItem(
    val title: String,
    @DrawableRes val icon: Int,
    val screenList: List<Screen>
) {
    object Home: BottomNavItem(
        title = "홈",
        icon = R.drawable.home,
        screenList = listOf(Screen.NoticeListScreen))

    object Scrap: BottomNavItem(
        title = "스크랩",
        icon = R.drawable.bookmark,
        screenList = listOf(Screen.ScrapScreen))

    object Subscribe: BottomNavItem(
        title = "구독",
        icon = R.drawable.subscribe,
        screenList = listOf(
            Screen.SubscribeScreen,
            Screen.SubscribeCustomAddScreen,
            Screen.SubscribeCustomUpdateScreen,
            Screen.UnivListScreen,
            Screen.DepartmentListScreen,
            Screen.NoticeBoardListScreen
        ))

    object Setting: BottomNavItem(
        title = "설정",
        icon = R.drawable.setting,
        screenList = listOf(
            Screen.SettingScreen,
            Screen.ComplainScreen
        ))
}
