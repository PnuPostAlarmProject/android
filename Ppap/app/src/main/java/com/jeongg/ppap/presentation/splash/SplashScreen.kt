package com.jeongg.ppap.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.rainbowColorsBrush
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
){
    val animation = remember { Animatable(initialValue = 0f) }
    val distance = with(LocalDensity.current){ 100.dp.toPx() }

    LaunchedEffect(key1 = true){
        splashAnimate(animation)
        val nextScreen = when (viewModel.isUserLoggedIn()) {
            true -> Screen.NoticeListScreen
            false -> Screen.LoginScreen
        }
        navController.popBackStack()
        navController.navigate(nextScreen.route)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(70.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SplashTitle()
            SplashCharacters(
                modifier = Modifier.graphicsLayer {
                    translationX = -animation.value * distance
                }
            )
        }
        Footer(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

suspend fun splashAnimate(animation: Animatable<Float, AnimationVector1D>) {
    delay(200)
    animation.animateTo(
        targetValue = 1f,
        animationSpec = keyframes {
            durationMillis = 300
            0.0f at 0 with LinearOutSlowInEasing
            1.0f at 1000 with LinearOutSlowInEasing
        }
    )
    delay(100)
}

@Composable
fun SplashTitle(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.name_yellow),
            contentDescription = "PPAP",
            modifier = Modifier.padding(bottom = 25.dp).width(117.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.name_description),
            contentDescription = "Pusan Post Alarm Project",
            modifier = Modifier.width(135.dp)
        )
    }
}
@Composable
fun SplashCharacters(
    modifier: Modifier
){
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        PenAnimation(
            modifier = Modifier.align(Alignment.CenterEnd),
            animationModifier = modifier.size(200.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pineapple),
                contentDescription = "pineapple character",
                modifier = Modifier.width(146.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = "apple character",
                modifier = Modifier.width(66.dp)
            )
        }

    }
}
@Composable
fun PenAnimation(
    modifier: Modifier,
    animationModifier: Modifier
){
    AnimatedVisibility(
        visible = true,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.pen),
            contentDescription = "pen",
            modifier = animationModifier
        )
    }
}
@Composable
fun Footer(
    modifier: Modifier
){
    Text(
        text = "@" + stringResource(R.string.team_name),
        style = MaterialTheme.typography.titleSmall.copy(
            brush = Brush.linearGradient(rainbowColorsBrush)
        ),
        modifier = modifier.padding(15.dp)
    )
}