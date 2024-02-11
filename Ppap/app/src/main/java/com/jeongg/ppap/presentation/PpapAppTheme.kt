package com.jeongg.ppap.presentation

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.jeongg.ppap.presentation.navigation.BottomNavigationBar
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.navigation.ppapGraph
import com.jeongg.ppap.theme.PpapTheme

@Composable
fun PpapAppTheme(
    navController: NavHostController
){
    PpapTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
        ) {
            Surface(
                modifier = Modifier.fillMaxSize().padding(it),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route,
                    route = "ppap_route"
                ){
                    ppapGraph(navController = navController)
                }
            }
        }
    }
}
