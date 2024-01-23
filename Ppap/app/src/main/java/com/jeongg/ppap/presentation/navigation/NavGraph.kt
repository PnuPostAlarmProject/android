package com.jeongg.ppap.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jeongg.ppap.presentation.login.LoginScreen
import com.jeongg.ppap.presentation.notice.NoticeListScreen
import com.jeongg.ppap.presentation.scrap.ScrapScreen
import com.jeongg.ppap.presentation.setting.SettingScreen
import com.jeongg.ppap.presentation.splash.SplashScreen
import com.jeongg.ppap.presentation.subscribe.SubscribeScreen
import com.jeongg.ppap.presentation.subscribe_add.SubscribeAddScreen

fun NavGraphBuilder.ppapGraph(
    navController: NavController
){
    composable(
        route = Screen.LoginScreen.route,
        content = { LoginScreen(navController) }
    )
    composable(
        route = Screen.SplashScreen.route,
        content = { SplashScreen(navController) }
    )
    composable(
        route = Screen.SubscribeScreen.route,
        content = { SubscribeScreen(navController) }
    )
    composable(
        route = Screen.SubscribeAddScreen.route + "?subscribeId={subscribeId}",
        arguments = listOf(
            navArgument("subscribeId"){
                type = NavType.LongType
                defaultValue = -1L
            },
        ),
        content = { SubscribeAddScreen(navController) }
    )
    composable(
        route = Screen.NoticeListScreen.route,
        content = { NoticeListScreen(navController) }
    )
    composable(
        route = Screen.ScrapScreen.route,
        content = { ScrapScreen(navController) }
    )
    composable(
        route = Screen.SettingScreen.route,
        content = { SettingScreen(navController)}
    )
}