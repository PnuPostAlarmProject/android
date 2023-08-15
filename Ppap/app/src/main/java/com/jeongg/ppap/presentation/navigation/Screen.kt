package com.jeongg.ppap.presentation.navigation

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object SplashScreen: Screen("splash_screen")
    object SubscribeScreen: Screen("subscribe_screen")
    object SubscribeAddScreen: Screen("subscribe_add_screen")
    object NoticeListScreen: Screen("notice_list_screen")
    object NoticeScrapScreen: Screen("notice_scrap_screen")
}