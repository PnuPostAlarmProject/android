package com.jeongg.ppap.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongg.ppap.compose.login.LoginScreen
import com.jeongg.ppap.compose.notice.NoticeListScreen
import com.jeongg.ppap.compose.notice.NoticeScrapScreen
import com.jeongg.ppap.compose.setting.SettingScreen
import com.jeongg.ppap.compose.splash.SplashScreen
import com.jeongg.ppap.compose.subscribe.SubscribeAddScreen
import com.jeongg.ppap.compose.subscribe.SubscribeScreen

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