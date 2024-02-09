package com.jeongg.ppap.presentation.setting_complain

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.presentation.component.PTextFieldWithCharacter
import com.jeongg.ppap.presentation.component.loading.PCircularProgress

@Composable
fun ComplainScreen(
    navController: NavController,
    viewModel: ComplainViewModel = hiltViewModel()
){
    if(viewModel.isLoading.value) PCircularProgress()
    PTextFieldWithCharacter(
        screenTitle = "건의 사항",
        title = "건의 내용",
        text = viewModel.content.value,
        onValueChange = { viewModel.enterContent(it) },
        buttonText = "작성 완료",
        placeholder = "더 나은 서비스를 위해 불편한 점, 에러 상황을 작성해주세요.",
        onButtonClick = { viewModel.sendEmail{ navController.navigateUp() } },
        minHeight = 160.dp,
    )
}