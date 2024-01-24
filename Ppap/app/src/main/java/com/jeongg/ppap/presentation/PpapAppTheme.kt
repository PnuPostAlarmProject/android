package com.jeongg.ppap.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeongg.ppap.presentation.navigation.BottomNavigationBar
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.navigation.ppapGraph
import com.jeongg.ppap.theme.PpapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PpapAppTheme(){
    PpapTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                if (shouldBottomBarVisible(navController)){
                    BottomNavigationBar(navController)
                }},
        ) {
            Surface(
                modifier = Modifier.fillMaxSize().padding(it),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ){
                    ppapGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun shouldBottomBarVisible(
    navController: NavController
): Boolean {
    val shouldNotShown = listOf(Screen.LoginScreen.route, Screen.SplashScreen.route)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return (currentRoute in shouldNotShown).not()

}