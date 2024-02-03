package com.jeongg.ppap.presentation.navigation

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object SplashScreen: Screen("splash_screen")
    object SubscribeScreen: Screen("subscribe_screen")
    object SubscribeCustomAddScreen: Screen("subscribe_custom_add_screen")
    object SubscribeCustomUpdateScreen: Screen("subscribe_custom_update_screen")
    object UnivListScreen: Screen("univ_list_screen")
    object DepartmentListScreen: Screen("department_list_screen")
    object NoticeBoardListScreen: Screen("notice_board_list_screen")
    object NoticeListScreen: Screen("notice_list_screen")
    object ScrapScreen: Screen("scrap_screen")
    object SettingScreen: Screen("setting_screen")
}