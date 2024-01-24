package com.jeongg.ppap.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.util.log

@Composable
fun BottomNavigationBar(
    navController: NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Scrap,
        BottomNavItem.Subscribe,
        BottomNavItem.Setting
    )
    Column {
        PDivider()
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .height(64.dp)
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            screens.forEach { screen ->
                AddItem(
                    bottomNavItem = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun RowScope.AddItem(
    bottomNavItem: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavController
){
    NavigationBarItem (
        icon = { NavigationItem(bottomNavItem) },
        selected = isSelected(currentDestination, bottomNavItem),
        onClick = {
            navController.navigate(bottomNavItem.screenList.first().route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.surface,
            unselectedIconColor = MaterialTheme.colorScheme.secondary,
            indicatorColor = MaterialTheme.colorScheme.background,
        ),
        interactionSource = NoRippleInteractionSource
    )
}

@Composable
private fun isSelected(
    currentDestination: NavDestination?,
    bottomNavItem: BottomNavItem
): Boolean {
    return currentDestination?.hierarchy?.any { navDestination ->
        // SubscribeAddScreen 의 경우 route에 navArgument가 포함되어있으므로 파싱이 필요함
        val route = navDestination.route?.split("?")?.get(0) ?: ""
        bottomNavItem.screenList.map { screen -> screen.route }.contains(route)
    } == true
}

@Composable
private fun NavigationItem(bottomNavItem: BottomNavItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(bottomNavItem.icon),
            contentDescription = bottomNavItem.title,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = bottomNavItem.title,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
