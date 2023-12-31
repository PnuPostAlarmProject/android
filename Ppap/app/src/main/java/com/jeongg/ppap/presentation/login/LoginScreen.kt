package com.jeongg.ppap.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.theme.Dimens
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest{ event ->
            when(event){
                is PEvent.SUCCESS -> {
                    navController.popBackStack()
                    navController.navigate(Screen.SubscribeScreen.route)
                }
                is PEvent.ERROR -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { "로그인 로딩중".log() }
            }
        }
    }
    Box(
        modifier = Modifier.padding(Dimens.PaddingNormal)
    ){
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = Dimens.PaddingExtraLarge)
            )
            PDivider(modifier = Modifier.padding(vertical = Dimens.PaddingSmall))
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = stringResource(R.string.app_description),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 100.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.kakao_login),
                    contentDescription = stringResource(R.string.login),
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { viewModel.kakaoLogin(context) }
                )
            }
        }
        Row(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.pineapple),
                contentDescription = stringResource(R.string.characters),
            )
            Image(
                painter = painterResource(id = R.drawable.apple_no_background),
                contentDescription = stringResource(R.string.characters),
            )
        }
    }
}