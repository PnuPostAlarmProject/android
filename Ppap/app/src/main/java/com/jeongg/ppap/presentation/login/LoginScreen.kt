package com.jeongg.ppap.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.ui.theme.Dimens

@Composable
fun LoginScreen(
    navController: NavController
){
    Box(
        modifier = Modifier.padding(Dimens.PaddingNormal)
    ){
        Column{
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = Dimens.PaddingExtraLarge)
            )
            PDivider(modifier = Modifier.padding(vertical = Dimens.PaddingSmall))
            Box(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = stringResource(R.string.app_description),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 100.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.kakao_login),
                    contentDescription = stringResource(R.string.login),
                    modifier = Modifier.width(100.dp).height(50.dp).align(Alignment.CenterEnd)
                        .clickable{navController.navigate(Screen.SubscribeScreen.route)}
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.characters),
            contentDescription = stringResource(R.string.characters),
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
        )
    }
}