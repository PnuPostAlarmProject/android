package com.jeongg.ppap.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jeongg.ppap.presentation.navigation.BottomNavigationBar
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.navigation.ppapGraph
import com.jeongg.ppap.theme.PpapTheme

@Composable
fun PpapAppTheme(){
    PpapTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
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