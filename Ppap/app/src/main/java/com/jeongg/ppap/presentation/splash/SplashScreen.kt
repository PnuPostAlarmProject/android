package com.jeongg.ppap.presentation.splash

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    var pineapple by remember { mutableStateOf(false) }
    var apple by remember { mutableStateOf(false) }
    var pencil by remember { mutableStateOf(false) }
    var fadeOut by remember { mutableStateOf(true) }
    var fadeIn by remember { mutableStateOf(false) }

    val animation = remember { Animatable(initialValue = 0f) }
    val distance = with(LocalDensity.current){ 180.dp.toPx() }
    LaunchedEffect(key1 = true){
        delay(400)
        apple = true
        delay(400)
        pineapple = true
        delay(700)
        pencil = true
        animation.animateTo(
            targetValue = 1f,
            animationSpec = keyframes {
                durationMillis = 400
                0.0f at 0 with LinearOutSlowInEasing
                1.0f at 200 with LinearOutSlowInEasing
            }
        )
        fadeOut = false
        delay(500)
        fadeIn = true
        delay(300)

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
        ScaleAnimation(
            isVisible = fadeOut
        ) {
            if (pencil) {
                Image(
                    painter = painterResource(R.drawable.pencil),
                    contentDescription = "pencil",
                    modifier = Modifier
                        .padding(bottom = 200.dp)
                        .align(Alignment.CenterEnd)
                        .graphicsLayer {
                            translationX = -animation.value * distance
                            translationY = animation.value * distance
                        }
                )
            }
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .align(Alignment.Center),
                verticalAlignment = Alignment.Bottom
            ) {
                FadeInAnimation(apple, R.drawable.apple_no_background)
                FadeInAnimation(pineapple, R.drawable.pineapple)
            }
        }
        ScaleAnimation(
            isVisible = fadeIn
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
       Text(
           text = "@" + stringResource(R.string.team_name),
           style = MaterialTheme.typography.titleSmall.copy(
               brush = Brush.linearGradient(rainbowColorsBrush)
           ),
           modifier = Modifier
               .padding(15.dp)
               .align(Alignment.BottomCenter),
       )
    }
}

@Composable
fun ScaleAnimation(
    isVisible: Boolean = true,
    content: @Composable (BoxScope.() -> Unit),
){
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = fadeOut(animationSpec = tween(200)) + scaleOut(),
        modifier = Modifier.fillMaxSize(),
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
@Composable
fun FadeInAnimation(
    isVisible: Boolean = true,
    @DrawableRes id: Int = R.drawable.pineapple
){
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000))
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = "animation",
        )
    }
}