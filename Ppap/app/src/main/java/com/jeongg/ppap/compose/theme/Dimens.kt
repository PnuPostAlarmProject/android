package com.jeongg.ppap.compose.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimens {
    val PaddingExtraLarge: Dp @Composable get() = 90.dp
    val PaddingLarge: Dp @Composable get() = 50.dp
    val PaddingNormal: Dp @Composable get() = 30.dp
    val PaddingSmall: Dp @Composable get() = 20.dp

    val ScreenPadding = PaddingValues(30.dp, 30.dp, 30.dp, 0.dp)
}