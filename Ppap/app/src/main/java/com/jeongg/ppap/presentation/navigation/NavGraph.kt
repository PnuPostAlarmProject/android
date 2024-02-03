package com.jeongg.ppap.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
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
import com.jeongg.ppap.presentation.subscribe_custom_add.SubscribeCustomAddScreen
import com.jeongg.ppap.presentation.subscribe_custom_update.SubscribeCustomUpdateScreen
import com.jeongg.ppap.presentation.subscribe_default_add.board_list.NoticeBoardListScreen
import com.jeongg.ppap.presentation.subscribe_default_add.univ_list.DepartmentListScreen
import com.jeongg.ppap.presentation.subscribe_default_add.univ_list.UnivListScreen
import com.jeongg.ppap.presentation.subscribe_default_add.univ_list.UnivViewModel

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
        route = Screen.SubscribeCustomAddScreen.route,
        content = { SubscribeCustomAddScreen(navController) }
    )
    composable(
        route = Screen.SubscribeCustomUpdateScreen.route + "?subscribeId={subscribeId}&subscribeName={name}",
        arguments = listOf(
            navArgument("subscribeId"){
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument("subscribeName"){
                type = NavType.StringType
                defaultValue = ""
            }
        ),
        content = { SubscribeCustomUpdateScreen(navController) }
    )
    composable(
        route = Screen.UnivListScreen.route,
        content = { entry ->
            val viewModel = entry.sharedViewModel<UnivViewModel>(navController)
            UnivListScreen(navController, viewModel)
        }
    )
    composable(
        route = Screen.DepartmentListScreen.route,
        content = {entry ->
            val viewModel = entry.sharedViewModel<UnivViewModel>(navController)
            DepartmentListScreen(navController, viewModel)
        }
    )
    composable(
        route = Screen.NoticeBoardListScreen.route + "?univId={univId}&title={title}",
        arguments = listOf(
            navArgument("univId"){
                type = NavType.LongType
                defaultValue = -1L
            },
            navArgument("title"){
                type = NavType.StringType
                defaultValue = "전체"
            }
        ),
        content = { NoticeBoardListScreen(navController) }
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

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}