package com.jeongg.ppap.presentation.navigation

import androidx.annotation.DrawableRes
import com.jeongg.ppap.R

sealed class BottomNavItem(
    val text: String,
    @DrawableRes val icon: Int,
    val screen: Screen
) {
    object Home: BottomNavItem("홈", R.drawable.home, Screen.NoticeListScreen)
    object Scrap: BottomNavItem("스크랩", R.drawable.bookmark, Screen.ScrapScreen)
    object Subscribe: BottomNavItem("구독", R.drawable.subscribe, Screen.SubscribeScreen)
    object Setting: BottomNavItem("설정", R.drawable.setting, Screen.SettingScreen)
}
