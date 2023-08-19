package com.jeongg.ppap.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jeongg.ppap.compose.navigation.Screen
import com.jeongg.ppap.compose.navigation.ppapGraph
import com.jeongg.ppap.compose.theme.PpapTheme

@Composable
fun PpapApp(){
    PpapTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ){
                ppapGraph(
                    navController = navController,
                    upPress = { navController.navigateUp() }
                )
            }
        }
    }
}