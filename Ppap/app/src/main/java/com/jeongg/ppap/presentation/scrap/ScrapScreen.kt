package com.jeongg.ppap.presentation.scrap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.presentation.component.util.ExitBackHandler
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.navigation.Screen

@Composable
fun ScrapScreen(
    navController: NavController,
    viewModel: ScrapViewModel = hiltViewModel()
){
    val selectedTabIndex = rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    ExitBackHandler()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrapTitle()
        PTabLayer(
            state = viewModel.state.value,
            selectedTabIndex = selectedTabIndex.intValue,
            onNavigate = { navController.navigate(Screen.SubscribeScreen.route) },
            onTabClick = { tabIndex, subscribeId ->
                if (selectedTabIndex.intValue != tabIndex){
                    selectedTabIndex.intValue = tabIndex
                    viewModel.getScrapPage(subscribeId)
                }
            }
        )
    }
}

@Composable
private fun ScrapTitle() {
    Text(
        text = "스크랩",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 20.dp, top = 20.dp)
    )
}