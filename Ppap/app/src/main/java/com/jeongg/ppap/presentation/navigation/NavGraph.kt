package com.jeongg.ppap.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongg.ppap.presentation.login.LoginScreen
import com.jeongg.ppap.presentation.splash.SplashScreen

fun NavGraphBuilder.ppapGraph(
    navController: NavController,
    upPress: () -> Unit
){
    composable(route = Screen.LoginScreen.route){ LoginScreen(navController)}
    composable(route = Screen.SplashScreen.route){ SplashScreen(navController) }
}