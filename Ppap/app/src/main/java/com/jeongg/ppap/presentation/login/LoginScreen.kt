package com.jeongg.ppap.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.util.noRippleClickable
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.Dimens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val context = LocalContext.current
    LaunchedEffectEvent(
        eventFlow = viewModel.eventFlow,
        onNavigate = {
            navController.popBackStack()
            navController.navigate(Screen.NoticeListScreen.route)
        }
    )
    Box(
        modifier = Modifier.padding(Dimens.PaddingNormal)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.name_yellow),
                contentDescription = "PPAP Logo",
                modifier = Modifier
                    .padding(top = Dimens.PaddingExtraLarge)
                    .width(117.dp)
            )
            PDivider(modifier = Modifier.padding(vertical = Dimens.PaddingSmall))
            KakaoLogin(onClick = { viewModel.kakaoLogin(context) })
        }
        LoginCharacters(modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
private fun LoginCharacters(
    modifier: Modifier
) {
    Row(
        modifier = modifier.size(200.dp),
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
private fun KakaoLogin(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.app_description),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 100.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.kakao_login),
            contentDescription = stringResource(R.string.login),
            modifier = Modifier
                .width(95.dp)
                .align(Alignment.CenterEnd)
                .noRippleClickable(onClick)
        )
    }
}