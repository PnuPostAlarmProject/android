package com.jeongg.ppap.presentation.subscribe_custom_update

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PTextFieldWithCharacter
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.navigation.Screen

@Composable
fun SubscribeCustomUpdateScreen(
    navController: NavController,
    viewModel: SubscribeCustomUpdateViewModel = hiltViewModel()
){
    LaunchedEffectEvent(
        eventFlow = viewModel.eventFlow,
        onNavigate = { navController.navigate(Screen.SubscribeScreen.route) }
    )
    PTextFieldWithCharacter(
        screenTitle = stringResource(R.string.update_subscribe_title),
        title = stringResource(R.string.subscribe_field_title),
        text = viewModel.subscribeName.value,
        onValueChange = { viewModel.enterTitle(it) },
        buttonText = "구독 이름 수정하기",
        onButtonClick = { viewModel.updateSubscribe() },
    )
}