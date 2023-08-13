package com.jeongg.ppap.presentation.navigation

sealed class Screen (val route: String){
    object LoginScreen: Screen("login_screen")
    object SplashScreen: Screen("splash_screen")
    object SubscribeScreen: Screen("subscribe_screen")
}