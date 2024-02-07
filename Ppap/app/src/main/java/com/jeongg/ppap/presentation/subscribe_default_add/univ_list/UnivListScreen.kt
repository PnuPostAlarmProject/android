package com.jeongg.ppap.presentation.subscribe_default_add.univ_list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.subscribe_default_add.NoticeBoardItem
import com.jeongg.ppap.presentation.subscribe_default_add.SubscribeAddCardTheme

@Composable
fun UnivListScreen(
    navController: NavController,
    viewModel: UnivViewModel = hiltViewModel()
){
    val univList = viewModel.univList.value.map { it.univ }
    SubscribeAddCardTheme(
        eventFlow = viewModel.eventFlow,
        onNavigate = { navController.navigate(Screen.DepartmentListScreen.route) },
        errorMessage = viewModel.errorMessage.value,
        isContentEmpty = univList.isEmpty()
    ){
        univList.forEachIndexed { index, text ->
            NoticeBoardItem(
                onClick = { viewModel.selectSubscribe(index) },
                text = text
            )
        }
    }
}