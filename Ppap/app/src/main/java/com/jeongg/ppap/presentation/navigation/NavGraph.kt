package com.jeongg.ppap.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongg.ppap.presentation.login.LoginScreen
import com.jeongg.ppap.presentation.notice.NoticeListScreen
import com.jeongg.ppap.presentation.notice.NoticeScrapScreen
import com.jeongg.ppap.presentation.setting.SettingScreen
import com.jeongg.ppap.presentation.splash.SplashScreen
import com.jeongg.ppap.presentation.subscribe.SubscribeAddScreen
import com.jeongg.ppap.presentation.subscribe.SubscribeScreen

fun NavGraphBuilder.ppapGraph(
    navController: NavController,
    upPress: () -> Unit
){
    composable(route = Screen.LoginScreen.route){ LoginScreen(navController)}
    composable(route = Screen.SplashScreen.route){ SplashScreen(navController) }
    composable(route = Screen.SubscribeScreen.route){ SubscribeScreen(navController, upPress) }
    composable(route = Screen.SubscribeAddScreen.route){ SubscribeAddScreen(navController, upPress) }
    composable(route = Screen.NoticeListScreen.route){ NoticeListScreen(navController)}
    composable(route = Screen.NoticeScrapScreen.route){ NoticeScrapScreen(navController, upPress)}
    composable(route = Screen.SettingScreen.route){ SettingScreen(navController, upPress)}
}