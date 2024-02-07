package com.jeongg.ppap.presentation.subscribe_default_add.univ_list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.subscribe_default_add.NoticeBoardItem
import com.jeongg.ppap.presentation.subscribe_default_add.SubscribeAddCardTheme

@Composable
fun DepartmentListScreen(
    navController: NavController,
    viewModel: UnivViewModel = hiltViewModel()
){
    val selected = viewModel.selected.value
    SubscribeAddCardTheme(
        text = "전체 / ${selected.univ}",
        eventFlow = viewModel.eventFlow,
        onNavigate = { navController.navigate(Screen.DepartmentListScreen.route) },
        errorMessage = viewModel.errorMessage.value,
        isContentEmpty = selected.departments.isEmpty()
    ){
        selected.departments.forEach { department ->
            val title = "전체 / ${selected.univ} / ${department.name}"
            val route = Screen.NoticeBoardListScreen.route + "?univId=${department.univId}&title=$title"
            NoticeBoardItem(
                onClick = { navController.navigate(route) },
                text = department.name
            )
        }
    }
}