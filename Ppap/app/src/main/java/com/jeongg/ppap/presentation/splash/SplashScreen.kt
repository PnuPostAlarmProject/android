package com.jeongg.ppap.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.ui.theme.typography
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
){
    LaunchedEffect(key1 = true){
        delay(1500L)
        navController.popBackStack()
        navController.navigate(Screen.LoginScreen.route)
    }
    Box{
       Image(
           painter = painterResource(id = R.drawable.characters),
           contentDescription = stringResource(R.string.characters),
           modifier = Modifier.size(200.dp).align(Alignment.Center)
       )
       Text(
           text = "@ " + stringResource(R.string.team_name),
           style = MaterialTheme.typography.titleSmall,
           modifier = Modifier.padding(15.dp).align(Alignment.BottomCenter),
       )
    }
}