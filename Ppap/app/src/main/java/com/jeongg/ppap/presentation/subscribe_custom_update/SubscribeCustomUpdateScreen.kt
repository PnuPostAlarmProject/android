package com.jeongg.ppap.presentation.subscribe_custom_update

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PTextFieldCard
import com.jeongg.ppap.presentation.component.addFocusCleaner
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.Dimens

@Composable
fun SubscribeCustomUpdateScreen(
    navController: NavController,
    viewModel: SubscribeCustomUpdateViewModel = hiltViewModel()
){
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    LaunchedEffectEvent(
        eventFlow = viewModel.eventFlow,
        onNavigate = { navController.navigate(Screen.SubscribeScreen.route) }
    )
    Column(
        modifier = Modifier
            .scrollable(scrollState, Orientation.Vertical)
            .addFocusCleaner(focusManager)
            .padding(Dimens.ScreenPadding),
    ){
        SubscribeCustomUpdateTitle()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
        ){
            PPAPCharacters()
            PTextFieldCard(
                title = stringResource(R.string.subscribe_field_title),
                text = viewModel.subscribeName.value,
                onValueChange = { viewModel.enterTitle(it) },
            )
            PButton(
                text = "구독 이름 수정하기",
                onClick = { viewModel.updateSubscribe() }
            )
        }
    }
}

@Composable
fun PPAPCharacters() {
    Row(
        modifier = Modifier.size(200.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.pineapple),
            contentDescription = stringResource(R.string.characters),
        )
        Image(
            painter = painterResource(id = R.drawable.apple),
            contentDescription = stringResource(R.string.characters),
        )
    }
}

@Composable
fun SubscribeCustomUpdateTitle() {
    Text(
        text = stringResource(R.string.update_subscribe_title),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 20.dp)
    )
}
